package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.*;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.OnMobKilledByDamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.ChatFormatting;

public class ResourceStats {

    public static DataPackStatAccessor<ResourceOnAction> RESOURCE_ON_ACTION = DatapackStatBuilder
            .<ResourceOnAction>of(x -> x.GUID(), x -> Elements.Physical)
            .addAllOfType(ResourceOnAction.allCombos())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.IS_BOOLEAN.get(x.action))
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resource)) // todo maybe make this not capped by leech
            .setLocName(x ->
                    Stat.format(
                            "Gain " + Stat.VAL1 + " " + x.resource.locname + " on " + x.actionName
                    )
            )
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.min = 0;
                x.is_perc = false;
                x.scaling = StatScaling.NORMAL;
            })
            .build();


    public static DataPackStatAccessor<LeechInfo> ELEMENT_LEECH_RESOURCE = DatapackStatBuilder
            .<LeechInfo>of(x -> x.GUID(), x -> x.element)
            .addAllOfType(LeechInfo.allCombos())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resourceType))
            .setLocName(x ->
                    Stat.format(
                            "Leech " + Stat.VAL1 + "% of your " + x.element.getIconNameFormat() + " Damage as " + x.resourceType.locname
                    )
            )
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.min = 0;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static DataPackStatAccessor<ResourceType> RESOURCE_ON_KILL = DatapackStatBuilder
            .<ResourceType>of(x -> x.id + "_on_kill", x -> Elements.ALL)
            .addAllOfType(ResourceType.values())
            .worksWithEvent(OnMobKilledByDamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e))
            .setLocName(x -> x.locname + " on Kill")
            .setLocDesc(x -> "Leeches resource every time you kill an enemy. This is capped as all other leech")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = false;
                x.scaling = StatScaling.NORMAL;
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<ResourceAndAttack> RESOURCE_ON_HIT = DatapackStatBuilder
            .<ResourceAndAttack>of(x -> x.GUID(), x -> Elements.ALL)
            .addAllOfType(ResourceAndAttack.allCombos())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.ATTACK_TYPE_MATCHES.get(x.attackType))
            //.addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK) // todo why did i do this?
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resource))
            .setLocName(x -> x.resource.locname + " on " + x.attackType.locname)
            .setLocDesc(x -> "Adds X amount of resource per hit to leech table and is capped by leech cap")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = false;
                x.scaling = StatScaling.NORMAL;
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<ResourceType> LEECH_CAP = DatapackStatBuilder
            .<ResourceType>of(x -> x.id + "_leech_cap", x -> Elements.ALL)
            .addAllOfType(ResourceType.values())
            //.worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setLocName(x -> x.locname + " Leech Cap")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 10;
                x.is_perc = true;
                x.scaling = StatScaling.NORMAL;
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> LOW_HP_HEALING = DatapackStatBuilder
            .ofSingle("low_hp_healing", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_TARGET_LOW)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Healing when Low")
            .setLocDesc(x -> "Boosts healing done to low hp/magic shield targets. If magic shield is higher, it checks that, otherwise it checks HP.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> OUT_OF_COMBAT_REGEN = DatapackStatBuilder
            .ofSingle("out_of_combat_regen", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addCondition(StatConditions.IS_NOT_IN_COMBAT)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Out of Combat Regen")
            .setLocDesc(x -> "Increases your out of combat regeneration")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.GREEN.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> HEAL_STRENGTH = DatapackStatBuilder
            .ofSingle("increase_healing", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Heal Strength")
            .setLocDesc(x -> "Increases spell related heals.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> TOTEM_RESTORATION_STRENGTH = DatapackStatBuilder
            .ofSingle("totem_resto", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.totem))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Your Totem restoration effects are " + Stat.VAL1 + "% stronger.")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.scaling = StatScaling.NONE;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> REJUV_HEAL_SELF = DatapackStatBuilder
            .ofSingle("rejuv_eff_on_self", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.rejuvenate))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Rejuvenation Healing")
            .setLocDesc(x -> "Increases Rejuvenation related heals on you.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -100;
                x.format = ChatFormatting.GREEN.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> HEALING_RECEIVED = DatapackStatBuilder
            .ofSingle("heal_effect_on_self", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Healing Received")
            .setLocDesc(x -> "Increases spell related heals on you.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -100;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> LIFESTEAL = DatapackStatBuilder
            .ofSingle("lifesteal", Elements.ALL)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
            .setLocName(x -> "Lifesteal")
            .setLocDesc(x -> "Restore % of damage as health.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> MANASTEAL = DatapackStatBuilder
            .ofSingle("manasteal", Elements.ALL)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.mana))
            .setLocName(x -> "Manasteal")
            .setLocDesc(x -> "Restore % of damage as mana.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> SPELL_LIFESTEAL = DatapackStatBuilder
            .ofSingle("spell_lifesteal", Elements.ALL)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
            .setLocName(x -> "Spell Lifesteal")
            .setLocDesc(x -> "Restore % of spell damage as health.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> SPELL_MSSTEAL = DatapackStatBuilder
            .ofSingle("spell_mssteal", Elements.ALL)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.magic_shield))
            .setLocName(x -> "Spell Magic Shield Steal") // need a better name than this lol
            .setLocDesc(x -> "Restore % of spell damage as magic shield.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DOT_LIFESTEAL = DatapackStatBuilder
            .ofSingle("dot_lifesteal", Elements.ALL)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.AFTER_DAMAGE_BONUSES)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
            .setLocName(x -> "Damage Over Time Lifesteal")
            .setLocDesc(x -> "Restore % of DoT damage as health.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> INCREASED_LEECH = DatapackStatBuilder
            .ofSingle("inc_leech", Elements.ALL)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(StatPriority.Damage.FINAL_DAMAGE)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.leech))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Leech Effect")
            .setLocDesc(x -> "Affects all resource leech stats like: onhit, leech dmg, on kill restore etc")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.RESTORATION;
            })
            .build();

    public static void init() {

    }
}
