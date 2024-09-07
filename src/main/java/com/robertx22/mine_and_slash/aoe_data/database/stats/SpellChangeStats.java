package com.robertx22.mine_and_slash.aoe_data.database.stats;

import com.robertx22.mine_and_slash.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.mine_and_slash.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.mine_and_slash.database.data.aura.AuraGems;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.mine_and_slash.uncommon.effectdatas.GenerateThreatEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.ThreatGenType;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import net.minecraft.ChatFormatting;

public class SpellChangeStats {
    public static DataPackStatAccessor<AuraGems.AuraInfo> SPECIFIC_AURA_COST = DatapackStatBuilder
            .<AuraGems.AuraInfo>of(x -> x.id + "_aura_cost", x -> Elements.Physical)
            .addAllOfType(AuraGems.ALL)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .setLocName(x -> Stat.format(x.name + " Augment Cost"))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.minus_is_good = true;
            })
            .build();
    public static DataPackStatAccessor MAX_SUMMON_CAPACITY = DatapackStatBuilder
            .ofSingle("max_total_summons", Elements.ALL)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.ADD_TOTAL_SUMMONS)
            .setLocName(x -> "Maximum Summons")
            .setLocDesc(x -> "You can summon more minions.")
            .modifyAfterDone(x ->
            {
                x.is_perc = false;
                x.base = 0;
                x.min = 0;
                x.max = 10;
            }).
            build();
    public static DataPackStatAccessor<EmptyAccessor> MANA_COST = DatapackStatBuilder
            .ofSingle("mana_cost", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.INCREASE_MANA_COST)
            .setLocName(x -> "Mana Cost")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -75;
                x.max = 300;
                x.minus_is_good = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> CAST_SPEED = DatapackStatBuilder
            .ofSingle("cast_speed", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.DECREASE_CAST_TIME)
            .addCondition(DatapackStatBuilder.EffectPlace.SECOND, StatConditions.SPELL_HAS_TAG.get(SpellTags.CAST_TO_CD))
            .addEffect(DatapackStatBuilder.EffectPlace.SECOND, StatEffects.APPLY_CAST_SPEED_TO_CD)
            .setLocName(x -> "Cast Speed")
            .setLocDesc(x -> "Affects amount of time needed to cast spells. If the spell is instant, it reduces the cooldown. Only works on spells tagged as Magic")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -90;
                x.max = 90;
            })
            .build();
    public static DataPackStatAccessor<SpellTag> CAST_TIME_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_cast_time", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())

            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.DECREASE_CAST_TIME)
            .setLocName(x -> x.locNameForLangFile() + " Cast Time")
            .setLocDesc(x -> "Reduces cast time of spells with this tag")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<SpellTag> COOLDOWN_REDUCTION_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_cdr", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.DECREASE_COOLDOWN)
            .setLocName(x -> x.locNameForLangFile() + " Spell Cooldown Reduction")
            .setLocDesc(x -> "Reduces spell cooldown of spells with the tag.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.max = 50;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> COOLDOWN_REDUCTION = DatapackStatBuilder
            .ofSingle("cdr", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.DECREASE_COOLDOWN)
            .setLocName(x -> "Cooldown Reduction")
            .setLocDesc(x -> "Reduces spell cooldown.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.max = 80;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> COOLDOWN_TICKS = DatapackStatBuilder
            .ofSingle("cd_ticks", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.DECREASE_COOLDOWN_BY_X_TICKS)
            .setLocName(x -> "Cooldown Ticks")
            .setLocDesc(x -> "Reduces spell cooldown by x ticks")
            .modifyAfterDone(x -> {
                x.is_perc = false;
                x.min = -15;
                x.max = 10000;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_SPEED = DatapackStatBuilder
            .ofSingle("faster_projectiles", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.INCREASE_PROJ_SPEED)
            .setLocName(x -> "Projectile Speed")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.icon = "\u27B9";
                x.format = ChatFormatting.GREEN.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_COUNT = DatapackStatBuilder
            .ofSingle("projectile_count", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.PROJECTILE_COUNT)
            .setLocName(x -> "Projectile Count")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = false;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_BARRAGE = DatapackStatBuilder
            .ofSingle("projectile_barrage", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.SET_BOOLEAN.get(EventData.BARRAGE))
            .setLocName(x -> "Projectiles Barrage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = false;
            })
            .build();

    // todo merge this into duration per spell tag
    public static DataPackStatAccessor<EmptyAccessor> SUMMON_DURATION = DatapackStatBuilder
            .ofSingle("summon_duration", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.summon))
            .addEffect(StatEffects.DURATION_INCREASE)
            .setLocName(x -> "Summon Duration")
            .setLocDesc(x -> "Your summons last longer (mobs like zombie, wolf etc summons)")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.icon = "\u27B9";
                x.format = ChatFormatting.GREEN.getName();
            })

            .build();
    public static DataPackStatAccessor<EmptyAccessor> TOTEM_DURATION = DatapackStatBuilder
            .ofSingle("totem_duration", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.totem))
            .addEffect(StatEffects.DURATION_INCREASE)
            .setLocName(x -> "Totem Duration")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.format = ChatFormatting.GREEN.getName();
            })

            .build();
    public static DataPackStatAccessor<EmptyAccessor> AGGRO_RADIUS = DatapackStatBuilder
            .ofSingle("aggro_radius", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.summon))
            .addEffect(StatEffects.AGGRO_INCREASE)
            .setLocName(x -> "Minion Aggro Radius")
            .setLocDesc(x -> "Higher radius means minions can travel further to kill stuff for you, lower means they will stay nearby more")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.format = ChatFormatting.GREEN.getName();
            })

            .build();
    public static DataPackStatAccessor<EmptyAccessor> INCREASED_AREA = DatapackStatBuilder
            .ofSingle("inc_aoe", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addEffect(StatEffects.INCREASE_AREA)
            .setLocName(x -> "Area of Effect")
            .setLocDesc(x -> "Spell aoe effects will be larger")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.max = 100;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PIERCING_PROJECTILES = DatapackStatBuilder
            .ofSingle("piercing_projectiles", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.SET_BOOLEAN.get(EventData.PIERCE))
            .setLocName(x -> "Piercing Projectiles")
            .setLocDesc(x -> "Makes spell pierce enemies and keep on")
            .modifyAfterDone(x -> {
                x.is_perc = false;
                x.is_long = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> THREAT_GENERATED = DatapackStatBuilder
            .ofSingle("threat_generated", Elements.Physical)
            .worksWithEvent(GenerateThreatEvent.ID)
            .setPriority(StatPriority.Spell.FIRST)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Threat Generated")
            .setLocDesc(x -> "Modifies amount of threat you generate. Mobs attack targets with highest threat.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> MORE_THREAT_WHEN_TAKING_DAMAGE = DatapackStatBuilder
            .ofSingle("more_threat_on_take_dmg", Elements.Physical)
            .worksWithEvent(GenerateThreatEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_THREAT_GEN_TYPE.get(ThreatGenType.take_dmg))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> Stat.format("You generate " + Stat.VAL1 + "% more threat when taking damage."))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static void init() {

    }
}
