package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;

import java.util.Arrays;
import java.util.Collection;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.ELEMENT;
import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.VALUE_CALCULATION;

public class DamageAction extends SpellAction {

    public DamageAction() {
        super(Arrays.asList(ELEMENT, VALUE_CALCULATION));
    }

    @Override
    public void tryActivate(Collection<LivingEntity> targets, SpellCtx ctx, MapHolder data) {

        if (!ctx.world.isClientSide) {
            Elements ele = data.getElement();
            ValueCalculation calc = data.get(VALUE_CALCULATION);

            int value = calc.getCalculatedValue(ctx.levelProvider);

            for (LivingEntity t : targets) {

                if (t == null) {
                    continue;
                }

                int stacks = 1;
                try {
                    if (data.has(MapField.EXILE_POTION_ID)) {
                        // if damage done by effect, multiple dmg by effect stacks.
                        Effect effect = SlashPotions.getExileEffect(data.get(MapField.EXILE_POTION_ID));
                        if (t.hasEffect(effect)) {
                            stacks = Load.Unit(t)
                                .getStatusEffectsData()
                                .get((ExileStatusEffect) effect).stacks;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                DamageEvent dmg = EventBuilder.ofSpellDamage(ctx.caster, t, value * stacks, ctx.calculatedSpellData.getSpell())
                    .build();
                if (data.has(MapField.DMG_EFFECT_TYPE)) {
                    dmg.data.setString(EventData.ATTACK_TYPE, data.getDmgEffectType()
                        .name());
                }
                dmg.setElement(ele);
                dmg.Activate();
            }
        }

    }

    public MapHolder create(ValueCalculation calc, Elements ele) {
        MapHolder dmg = new MapHolder();
        dmg.type = GUID();
        dmg.put(VALUE_CALCULATION, calc);
        dmg.put(ELEMENT, ele.name());
        return dmg;
    }

    @Override
    public String GUID() {
        return "damage";
    }

}
