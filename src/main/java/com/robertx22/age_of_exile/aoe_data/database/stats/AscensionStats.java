package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.action.SetCooldownEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class AscensionStats {

    // todo whats the best way to make it so after i cast a spell, it removes a stack of buff?

    public static DataPackStatAccessor<EmptyAccessor> BARRAGE_SELF_BUFF_ON_SPELL_CRIT = DatapackStatBuilder
            .ofSingle("chance_of_barrage_on_spell_crit", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.IF_CRIT)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.SPELL_NOT_HAVE_TAG.get(SpellTags.MISSILE))
            .addCondition(StatConditions.IS_NOT_ON_COOLDOWN.get(SetCooldownEffect.MISSILE_BARRAGE.cd_id))
            .addEffect(StatEffects.GIVE_SELF_EFFECT_30_SEC.get(ModEffects.MISSILE_BARRAGE))
            .addEffect(StatEffects.SET_COOLDOWN.get(SetCooldownEffect.MISSILE_BARRAGE))
            .setLocName(x -> Stat.format(
                    "Your non Missile Spell Criticals have " + Stat.VAL1 + "% chance of giving you " + ModEffects.MISSILE_BARRAGE.locname
            ))
            .setLocDesc(x -> "Chance to give effect")
            .modifyAfterDone(x -> {
                x.setChanceBasedStatDefaults();
            })
            .build();

    public static void init() {


    }
}
