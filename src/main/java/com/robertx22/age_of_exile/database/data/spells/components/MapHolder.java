package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction.GiveOrTake;
import com.robertx22.age_of_exile.database.data.spells.components.actions.PositionSource;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonProjectileAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.function.Function;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.EXILE_POTION_ID;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.POTION_ID;

public class MapHolder {

    public String type;

    private HashMap<String, Object> map = new HashMap<>();

    public boolean has(MapField f) {
        return map.containsKey(f.GUID());
    }

    public <T> MapHolder put(MapField<T> field, T t) {
        if (field == MapField.VALUE_CALCULATION) {
            this.map.put(field.GUID(), ((ValueCalculation) t).GUID());
            return this;
        }
        this.map.put(field.GUID(), t);
        return this;
    }

    public <T> void modify(MapField<T> f, T def, Function<T, T> edit) {
        T val = getOrDefault(f, def);
        put(f, edit.apply(val));
    }

    public <T> T get(MapField<T> field) {
        if (field == MapField.VALUE_CALCULATION) {
            return (T) ExileDB.ValueCalculations()
                    .get((String) map.get(field.GUID()));
        }
        return (T) map.get(field.GUID());
    }

    public ExileEffect getExileEffect() {
        return ExileDB.ExileEffects().get(get(EXILE_POTION_ID));
    }

    // todo test if this works
    public <T extends Enum> T getEnum(T en, MapField<String> f) {
        var o = get(f);
        return (T) en.valueOf(en.getClass(), o);
    }

    public AttackType getDmgEffectType() {
        return AttackType.valueOf(get(MapField.DMG_EFFECT_TYPE));
    }

    public MobEffect getPotion() {
        return BuiltInRegistries.MOB_EFFECT.get(new ResourceLocation(get(POTION_ID)));
    }

    public Elements getElement() {
        return Elements.valueOf(get(MapField.ELEMENT));
    }

    public SummonType getSummonType() {
        return SummonType.valueOf(get(MapField.SUMMON_TYPE));
    }

    public DashUtils.Way getPushWay() {
        return DashUtils.Way.valueOf(get(MapField.PUSH_WAY));
    }

    public AggroAction.Type getAggro() {
        return AggroAction.Type.valueOf(get(MapField.AGGRO_TYPE));
    }

    public GiveOrTake getPotionAction() {
        return GiveOrTake.valueOf(get(MapField.POTION_ACTION));
    }

    public SummonProjectileAction.ShootWay getOrDefault(SummonProjectileAction.ShootWay way) {
        String f = getOrDefault(MapField.SHOOT_DIRECTION, "");
        if (!f.isEmpty() && SummonProjectileAction.ShootWay.valueOf(f) != null) {
            return SummonProjectileAction.ShootWay.valueOf(f);
        } else {
            return way;
        }
    }

    public PositionSource getOrDefault(PositionSource way) {
        String f = getOrDefault(MapField.POS_SOURCE, "");
        if (!f.isEmpty() && PositionSource.valueOf(f) != null) {
            return PositionSource.valueOf(f);
        } else {
            return way;
        }
    }

    /*public SimpleParticleType getParticle() {
        return (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(get(MapField.PARTICLE_TYPE)));
    }*/

    public Block getBlock() {
        return VanillaUTIL.REGISTRY.blocks().get(new ResourceLocation(get(MapField.BLOCK)));
    }

    public SoundEvent getSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(get(MapField.SOUND)));
    }

    public EntityFinder.SelectionType getSelectionType() {
        return EntityFinder.SelectionType.valueOf(get(MapField.SELECTION_TYPE));
    }

    public SetAdd getSetAdd() {
        return SetAdd.valueOf(get(MapField.SET_ADD));
    }

    public ParticleShape getParticleShape() {
        String str = get(MapField.PARTICLE_SHAPE);

        if (str != null && !str.isEmpty()) {
            return ParticleShape.valueOf(str);
        } else {
            return ParticleShape.CIRCLE;
        }
    }

    public AllyOrEnemy getEntityPredicate() {
        return AllyOrEnemy.valueOf(get(MapField.ENTITY_PREDICATE));
    }

    public <T> T getOrDefault(MapField<T> field, T defa) {
        return (T) map.getOrDefault(field.GUID(), defa);
    }

}
