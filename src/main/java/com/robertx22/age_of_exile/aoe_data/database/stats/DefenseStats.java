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
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class DefenseStats {
   
    public static DataPackStatAccessor<Elements> ALWAYS_CRIT_WHEN_HIT_BY_ELEMENT = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_vuln_crit", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.BEFORE_DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.SET_IS_CRIT)
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
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
            .setLocName(x -> "Damage Received")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.max = 90;
            })
            .build();
    public static DataPackStatAccessor<PlayStyle> STYLE_DAMAGE_RECEIVED = DatapackStatBuilder
            .<PlayStyle>of(x -> x.id + "_dmg_received", x -> Elements.Physical)
            .addAllOfType(PlayStyle.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x.getTag()))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
            .setLocName(x -> x.name + " Damage Received")
            .setLocDesc(x -> "Magic damage are spells that have tag Magic etc")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = Stat.StatGroup.Misc;
                x.max = 75;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE_RECEIVED = DatapackStatBuilder
            .ofSingle("proj_dmg_received", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_ANY_PROJECTILE)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
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
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE)
            .setLocName(x -> x.locNameForLangFile() + " Damage Taken")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static void init() {

    }
}
