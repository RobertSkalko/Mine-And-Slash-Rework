package com.robertx22.age_of_exile.vanilla_mc.potion_effects.types;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class ExileStatusEffect extends MobEffect implements IGUID, IOneOfATypePotion {

    String exileEffectId;

    public EffectType type;

    public ExileStatusEffect(EffectType type, int numericId) {
        super(type.type, 0);
        this.exileEffectId = getIdPath(type, numericId);
        this.type = type;
    }

    public static String getIdPath(EffectType type, int num) {
        if (type == EffectType.beneficial) {
            return "beneficial/" + num;
        }
        if (type == EffectType.negative) {
            return "negative/" + num;
        }
        if (type == EffectType.buff) {
            return "buff/" + num;
        }

        return "neutral/" + num;
    }

    public boolean hasExileRegistry() {
        return ExileDB.ExileEffects()
                .isRegistered(exileEffectId);
    }

    public ExileEffect getExileEffect() {
        return ExileDB.ExileEffects()
                .get(exileEffectId);
    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en)
                .getStatusEffectsData()
                .get(this);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributes, int amplifier) {

        try {
            ExileEffect exect = getExileEffect();
            ExileEffectInstanceData data = getSavedData(entity);

            if (data != null) {
                int stacks = data.stacks;
                exect.mc_stats.forEach(x -> x.applyVanillaStats(entity, stacks));
                Load.Unit(entity).forceRecalculateStats();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.addAttributeModifiers(entity, attributes, amplifier);

    }

    @Override
    public void removeAttributeModifiers(LivingEntity target, AttributeMap attributes,
                                         int amplifier) {

        try {

            ExileEffect exileEffect = getExileEffect();
            exileEffect.mc_stats.forEach(x -> x.removeVanillaStats(target));

            ExileEffectInstanceData data = getSavedData(target);

            if (data != null) {
                LivingEntity caster = data.getCaster(target.level());
                if (caster != null && exileEffect.spell != null) {
                    SpellCtx ctx = SpellCtx.onExpire(caster, target, data.calcSpell);
                    exileEffect.spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
                }
            }

            EntityData unitdata = Load.Unit(target);
            unitdata.getStatusEffectsData()
                    .get(this).stacks = 0;
            unitdata.setEquipsChanged(true);

            super.removeAttributeModifiers(target, attributes, amplifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }


    @Override
    public String GUID() {
        return exileEffectId;
    }


    @Override
    public String getOneOfATypeType() {
        return getExileEffect().one_of_a_kind_id;
    }
}
