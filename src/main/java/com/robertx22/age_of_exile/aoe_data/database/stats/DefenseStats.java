package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class DefenseStats {

    public static DataPackStatAccessor<Elements> ALWAYS_CRIT_WHEN_HIT_BY_ELEMENT = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_vuln_crit", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.BEFORE_DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.SET_BOOLEAN.get(EventData.CRIT))
            .setLocName(x -> Stat.format(x.dmgName + " Damage always crits you."))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_RECEIVED = DatapackStatBuilder
            .ofSingle("dmg_received", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage Received")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.max = 90;
            })
            .build();

    public static DataPackStatAccessor<Elements> ELEMENTAL_DAMAGE_REDUCTION = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_dmg_reduction", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(x -> StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.Layers.DAMAGE_REDUCTION)
            .setLocName(x -> x.dmgName + " Damage Reduction")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.min = -500;
                x.max = 75;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_REDUCTION = DatapackStatBuilder
            .<EmptyAccessor>ofSingle("dmg_reduction", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addEffect(StatEffects.Layers.DAMAGE_REDUCTION)
            .setLocName(x -> "Damage Reduction")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.min = -500;
                x.max = 75;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_REDUCTION_CHANCE = DatapackStatBuilder
            .<EmptyAccessor>ofSingle("dmg_reduction_chance", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addEffect(StatEffects.Layers.DAMAGE_REDUCTION_50)
            .setLocName(x -> "Damage Reduction Chance").
            setLocDesc(x -> "Chance to reduce damage by 50%. This stacks with other [Damage Reduction]s").
            modifyAfterDone(x ->
            {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.min = 0;
                x.max = 100;
            })
                    .

            build();


    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE_RECEIVED = DatapackStatBuilder
            .ofSingle("proj_dmg_received", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_ANY_PROJECTILE)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Projectile Damage Receieved")
            .setLocDesc(x -> "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
            })
            .build();
    public static DataPackStatAccessor<SpellTag> DAMAGE_TAKEN_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_spell_dmg_taken", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.locNameForLangFile() + " Damage Taken")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static void init() {

    }
}
