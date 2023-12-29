package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.*;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.Stat.StatGroup;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.ModTag;
import com.robertx22.age_of_exile.tags.TagType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.tags.imp.EffectTag;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.effectdatas.*;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

import java.util.Arrays;

import static com.robertx22.age_of_exile.database.data.stats.Stat.VAL1;
import static com.robertx22.age_of_exile.database.data.stats.Stat.format;

public class Stats implements ExileRegistryInit {

    public static void loadClass() {

    }


    public static DataPackStatAccessor<EmptyAccessor> EFFECT_DURATION_YOU_CAST = DatapackStatBuilder
            .ofSingle("eff_dur_u_cast", Elements.Physical)
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addEffect(e -> StatEffects.INCREASE_EFFECT_DURATION)
            .setLocName(x -> "Effect Duration")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EffectTag> EFFECT_DURATION_YOU_CAST_PER_TAG = DatapackStatBuilder
            .<EffectTag>of(x -> x.GUID() + "_eff_dur_u_cast", x -> Elements.Physical)
            .addAllOfType(EffectTag.getAll())
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(e -> StatEffects.INCREASE_EFFECT_DURATION)
            .setLocName(x -> x.locNameLangFileGUID() + " Effect Duration")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EffectCtx> GIVE_EFFECT_TO_ALLIES_IN_RADIUS = DatapackStatBuilder
            .<EffectCtx>of(x -> "give_" + x.id + "_to_allies_in_aoe", x -> x.element)
            .addAllOfType(Arrays.asList(
                    ModEffects.REJUVENATE
            ))
            .worksWithEvent(RestoreResourceEvent.ID) // todo should be tick event, BUT LAG
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addCondition(StatConditions.IS_IN_COMBAT)
            .addEffect(e -> StatEffects.GIVE_EFFECT_IN_AOE.get(e))
            .setLocName(x -> Stat.format(
                    "Give " + x.locname + " to allies in Radius."
            ))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 1;
                x.is_long = true;
            })
            .build();

    public static DataPackStatAccessor<EffectCtx> GIVE_EFFECT_TO_SELF_ON_TICK = DatapackStatBuilder
            .<EffectCtx>of(x -> "give_" + x.id + "_to_self_on_tick", x -> x.element)
            .addAllOfType(Arrays.asList(
                    ModEffects.TAUNT_STANCE
            ))
            .worksWithEvent(RestoreResourceEvent.ID) // todo should be tick event, BUT LAG
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addEffect(e -> StatEffects.GIVE_SELF_EFFECT.get(e))
            .setLocName(x -> Stat.format(
                    "Give " + x.locname + " to self"
            ))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 1;
                x.is_long = true;
            })
            .build();

    public static DataPackStatAccessor<LeechInfo> ELEMENT_LEECH_RESOURCE = DatapackStatBuilder
            .<LeechInfo>of(x -> x.element.guidName + "_" + x.resourceType.id + "_leech", x -> x.element)
            .addAllOfType(LeechInfo.allCombos())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
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

    public static DataPackStatAccessor<Elements> ALWAYS_CRIT_WHEN_HIT_BY_ELEMENT = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_vuln_crit", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
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


    public static DataPackStatAccessor<Elements> ELEMENTAL_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> "all_" + x.guidName + "_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.dmgName + " Damage")
            .setLocDesc(x -> "Increases All dmg of that element, both spells and attacks")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = StatGroup.ELEMENTAL;
            })
            .build();

    /*
    public static DataPackStatAccessor<SummonType> MAX_SUMMONS = DatapackStatBuilder
            .<SummonType>of(x -> "max_" + x.id + "_summons", x -> Elements.All)
            .addAllOfType(SummonType.values())
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.IS_SUMMON_TYPE.get(x))
            .addEffect(StatEffects.ADD_TO_MAX_SUMMONS)
            .setLocName(x -> "Maximum " + x.name + " Summons")
            .setLocDesc(x -> "You can summon more types of that summon.")
            .modifyAfterDone(x ->
            {
                x.is_perc = false;
                x.base = 0;
                x.min = -100;
                x.max = 10;
            }).
            build();

     */

    public static DataPackStatAccessor MAX_SUMMON_CAPACITY = DatapackStatBuilder
            .ofSingle("max_total_summons", Elements.All)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.ADD_TOTAL_SUMMONS)
            .setLocName(x -> "Maximum Summons")
            .setLocDesc(x -> "You can summon more minions.")
            .modifyAfterDone(x ->
            {
                x.is_perc = false;
                x.base = 0;
                x.min = -100;
                x.max = 10;
            }).
            build();

    public static DataPackStatAccessor<PlayStyle> STYLE_DAMAGE = DatapackStatBuilder
            .<PlayStyle>of(x -> x.id + "_dmg", x -> Elements.Physical)
            .addAllOfType(PlayStyle.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x.getTag()))
            .setUsesMoreMultiplier()
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.name + " Damage")
            .setLocDesc(x -> "Magic damage are mage spells, like fireball.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.group = StatGroup.Misc;
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_RECEIVED = DatapackStatBuilder
            .ofSingle("dmg_received", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Target)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage Received")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = StatGroup.Misc;
                x.max = 90;
            })
            .build();

    public static DataPackStatAccessor<PlayStyle> STYLE_DAMAGE_RECEIVED = DatapackStatBuilder
            .<PlayStyle>of(x -> x.id + "_dmg_received", x -> Elements.Physical)
            .addAllOfType(PlayStyle.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Target)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x.getTag()))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.name + " Damage Received")
            .setLocDesc(x -> "Magic damage are mage spells, like fireball.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
                x.group = StatGroup.Misc;
                x.max = 75;
            })
            .build();

    public static DataPackStatAccessor<Elements> ELEMENTAL_SPELL_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> "spell_" + x.guidName + "_damage", x -> x)
            .addAllOfType(Elements.getEverythingBesidesPhysical())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.dmgName + " Spells Damage")
            .setLocDesc(x -> "Increases damage of spells of that element.")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = StatGroup.ELEMENTAL;
            })
            .build();

    public static DataPackStatAccessor<WeaponTypes> WEAPON_DAMAGE = DatapackStatBuilder
            .<WeaponTypes>of(x -> x.id + "_damage", x -> Elements.Physical)
            .addAllOfType(WeaponTypes.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.WEAPON_TYPE_MATCHES.get(x))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.locName() + " Damage")
            .setLocDesc(x -> "Increases damage done if it was caused by that weapon")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = StatGroup.WEAPON;
            })
            .build();

    public static DataPackStatAccessor<WeaponTypes> ELEMENTAL_WEAPON_DAMAGE = DatapackStatBuilder
            .<WeaponTypes>of(x -> "ele_" + x.id + "_damage", x -> Elements.Physical)
            .addAllOfType(WeaponTypes.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.WEAPON_TYPE_MATCHES.get(x))
            .addCondition(StatConditions.IS_ELEMENTAL)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Elemental " + x.locName() + " Damage")
            .setLocDesc(x -> "Increases elemental damage done if it was caused by that weapon")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = StatGroup.WEAPON;
            })
            .build();

    public static DataPackStatAccessor<Elements> ELEMENTAL_ANY_WEAPON_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_any_wep_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.dmgName + " Weapon Damage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = StatGroup.WEAPON;
            })
            .build();

    public static DataPackStatAccessor<ResourceType> RESOURCE_ON_KILL = DatapackStatBuilder
            .<ResourceType>of(x -> x.id + "_on_kill", x -> Elements.All)
            .addAllOfType(Arrays.asList(
                    ResourceType.health,
                    ResourceType.mana
            ))
            .worksWithEvent(OnMobKilledByDamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e))
            .setLocName(x -> x.locname + " on Kill")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = false;
                x.scaling = StatScaling.NORMAL;
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<ResourceAndAttack> RESOURCE_ON_HIT = DatapackStatBuilder
            .<ResourceAndAttack>of(x -> x.resource.id + "_on_" + x.attackType.id + "_hit", x -> Elements.All)
            .addAllOfType(ResourceAndAttack.allCombos())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.ATTACK_TYPE_MATCHES.get(x.attackType))
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
            .addEffect(e -> StatEffects.LEECH_RESTORE_RESOURCE_BASED_ON_STAT_DATA.get(e.resource))
            .setLocName(x -> x.resource.locname + " on " + x.attackType.locname)
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = false;
                x.scaling = StatScaling.NORMAL;
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<ResourceType> LEECH_CAP = DatapackStatBuilder
            .<ResourceType>of(x -> x.id + "_leech_cap", x -> Elements.All)
            .addAllOfType(ResourceType.values())
            //.worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .setLocName(x -> "Max " + x.locname + " Leech Rate")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.scaling = StatScaling.NORMAL;
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EffectCtx> CHANCE_OF_APPLYING_EFFECT = DatapackStatBuilder
            .<EffectCtx>of(x -> "chance_of_" + x.id, x -> x.element)
            .addAllOfType(Arrays.asList(
                            ModEffects.BLIND,
                            ModEffects.SLOW
                    )
            )
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
            .addEffect(x -> StatEffects.GIVE_EFFECT_TO_TARGET.get(x))
            .setLocName(x -> Stat.format(
                    "Your " + x.element.getIconNameFormat() + " Attacks have " + Stat.VAL1 + "% chance of applying " + x.locname
            ))
            .setLocDesc(x -> "Chance to give effect")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.max = 100;
                x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> CRIT_CHANCE = DatapackStatBuilder
            .ofSingle("critical_hit", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            //.addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
            .addEffect(StatEffects.SET_IS_CRIT)
            .setLocName(x -> "Crit Chance")
            .setLocDesc(x -> "Chance to multiply attack damage by critical damage")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 1;
                x.max = 100;
                x.min = 0;
                x.group = StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.YELLOW.getName();
            })
            .build();


    public static DataPackStatAccessor<Elements> ELEMENT_CRIT_DAMAGE_TAKEN = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_crit_taken", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IF_CRIT)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.MULTIPLY_VALUE)
            .setLocName(x -> x.dmgName + " Crit DMG Taken")
            .setLocDesc(x -> "If Critical of that element, multiply by x")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.max = 500;
                x.group = StatGroup.MAIN;

                x.icon = "\u2694";
                x.format = ChatFormatting.RED.getName();

            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> CRIT_DAMAGE = DatapackStatBuilder
            .ofSingle("critical_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_CRIT)
            //addCondition(StatConditions.IS_ATTACK_OR_SPELL_ATTACK)
            .addEffect(StatEffects.MULTIPLY_VALUE)
            .setLocName(x -> "Crit Damage")
            .setLocDesc(x -> "If Critical, multiply by x")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 100;
                x.min = 0;
                x.max = 500;
                x.group = StatGroup.MAIN;

                x.icon = "\u2694";
                x.format = ChatFormatting.RED.getName();

            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> NON_CRIT_DAMAGE = DatapackStatBuilder
            .ofSingle("non_crit_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(99)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IF_NOT_CRIT)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Non Critical Damage")
            .setLocDesc(x -> "If not a Critical, multiply by x")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.min = -100;
                x.max = 500;
                x.group = StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> ACCURACY = DatapackStatBuilder
            .ofSingle("accuracy", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.SET_ACCURACY)
            .setLocName(x -> "Accuracy")
            .setLocDesc(x -> "Increases your chance to hit, low accuracy also causes crits to fail. Specifically it decreases opponent's chance to dodge")
            .modifyAfterDone(x -> {
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NORMAL;
                x.group = StatGroup.MAIN;
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE = DatapackStatBuilder
            .ofSingle("projectile_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_ANY_PROJECTILE)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Projectile Damage")
            .setLocDesc(x -> "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.icon = "\u27B9";
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE_RECEIVED = DatapackStatBuilder
            .ofSingle("proj_dmg_received", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_ANY_PROJECTILE)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Projectile Damage Receieved")
            .setLocDesc(x -> "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> AREA_DAMAGE = DatapackStatBuilder
            .ofSingle("area_dmg", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Area Damage")
            .setLocDesc(x -> "Affects dmg done by area of effect abilities. Think meteor or other large aoe spells")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.BLUE.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> TRAP_AREA_DAMAGE = DatapackStatBuilder
            .ofSingle("trap_area_dmg", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.trap))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Trap Area Damage")
            .setLocDesc(x -> "Affects trap dmg done by area of effect abilities.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.BLUE.getName();
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> DOT_DAMAGE = DatapackStatBuilder
            .ofSingle("dot_dmg", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage Over Time")
            .setLocDesc(x -> "Increases dmg of effects that do damage over time, like burn")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> TOTAL_DAMAGE = DatapackStatBuilder
            .ofSingle("total_damage", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Total Damage")
            .setLocDesc(x -> "Increases all your damage.")
            .modifyAfterDone(x -> {
                x.scaling = StatScaling.NONE;
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> ATTACK_DAMAGE = DatapackStatBuilder
            .ofSingle("attack_damage", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Attack Damage")
            .setLocDesc(x -> "Increases all attack damage.")
            .modifyAfterDone(x -> {
                x.scaling = StatScaling.NONE;
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<Elements> ELE_DOT_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_dot_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.dmgName + " Damage Over Time")
            .setLocDesc(x -> "Fire damage over time increases damage of burn, for example.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.Misc;
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> LOW_HP_HEALING = DatapackStatBuilder
            .ofSingle("low_hp_healing", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_TARGET_LOW)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Healing when Low")
            .setLocDesc(x -> "Boosts healing done to low hp/magic shield targets. If magic shield is higher, it checks that, otherwise it checks HP.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> OUT_OF_COMBAT_REGEN = DatapackStatBuilder
            .ofSingle("out_of_combat_regen", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.regen))
            .addCondition(StatConditions.IS_NOT_IN_COMBAT)
            .addEffect(StatEffects.MULTIPLY_VALUE)
            .setLocName(x -> "Out of Combat Regen")
            .setLocDesc(x -> "Increases your out of combat regeneration")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.GREEN.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> HEAL_STRENGTH = DatapackStatBuilder
            .ofSingle("increase_healing", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Heal Strength")
            .setLocDesc(x -> "Increases spell related heals.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> TOTEM_RESTORATION_STRENGTH = DatapackStatBuilder
            .ofSingle("totem_resto", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.totem))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Your Totem restoration effects are " + Stat.VAL1 + "% stronger.")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.scaling = StatScaling.NONE;
                x.base = 0;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> REJUV_HEAL_SELF = DatapackStatBuilder
            .ofSingle("rejuv_eff_on_self", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.rejuvenate))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Rejuvenation Healing")
            .setLocDesc(x -> "Increases Rejuvenation related heals on you.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -100;
                x.format = ChatFormatting.GREEN.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> HEALING_RECEIVED = DatapackStatBuilder
            .ofSingle("heal_effect_on_self", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Target)
            .addCondition(StatConditions.IS_SPELL)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.health))
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.heal))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Healing Received")
            .setLocDesc(x -> "Increases spell related heals on you.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -100;
                x.format = ChatFormatting.YELLOW.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> LIFESTEAL = DatapackStatBuilder
            .ofSingle("lifesteal", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
            .setLocName(x -> "Lifesteal")
            .setLocDesc(x -> "Restore % of damage as health.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> MANASTEAL = DatapackStatBuilder
            .ofSingle("manasteal", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.mana))
            .setLocName(x -> "Manasteal")
            .setLocDesc(x -> "Restore % of damage as mana.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> SPELL_LIFESTEAL = DatapackStatBuilder
            .ofSingle("spell_lifesteal", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.health))
            .setLocName(x -> "Spell Lifesteal")
            .setLocDesc(x -> "Restore % of spell damage as health.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> SPELL_MSSTEAL = DatapackStatBuilder
            .ofSingle("spell_mssteal", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addCondition(x -> StatConditions.IS_NOT_SUMMON_ATTACK)
            .addEffect(StatEffects.LEECH_PERCENT_OF_DAMAGE_AS_RESOURCE.get(ResourceType.magic_shield))
            .setLocName(x -> "Spell Magic Shield Steal") // need a better name than this lol
            .setLocDesc(x -> "Restore % of spell damage as magic shield.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DOT_LIFESTEAL = DatapackStatBuilder
            .ofSingle("dot_lifesteal", Elements.All)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
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
                x.group = StatGroup.RESTORATION;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> INCREASED_LEECH = DatapackStatBuilder
            .ofSingle("inc_leech", Elements.All)
            .worksWithEvent(RestoreResourceEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESTORE_TYPE.get(RestoreType.leech))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Leech Effect")
            .setLocDesc(x -> "Affects all resource leech stats like: onhit, leech dmg, on kill restore etc")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NONE;
                x.format = ChatFormatting.RED.getName();
                x.group = StatGroup.RESTORATION;
            })
            .build();


    public static DataPackStatAccessor<EmptyAccessor> MANA_COST = DatapackStatBuilder
            .ofSingle("mana_cost", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.INCREASE_MANA_COST)
            .setLocName(x -> "Mana Cost")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -75;
                x.max = 300;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> CAST_SPEED = DatapackStatBuilder
            .ofSingle("cast_speed", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.DECREASE_CAST_TIME)
            .addEffect(StatEffects.APPLY_CAST_SPEED_TO_CD)
            .setLocName(x -> "Cast Speed")
            .setLocDesc(x -> "Affects amount of time needed to cast spells. If the spell is instant, it reduces the cooldown. Only works on spells tagged as Magic")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.min = -90;
                x.max = 90;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DOUBLE_ATTACK_DAMAGE = DatapackStatBuilder
            .ofSingle("double_attack_chance", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(IStatEffect.Priority.Last.priority)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.DOUBLE_DAMAGE)
            .setLocName(x -> "Double Attack Damage Chance")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.max = 100;
            })
            .build();
    public static DataPackStatAccessor<SpellTag> DAMAGE_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_spell_dmg", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.locNameForLangFile() + " Damage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<SpellTag> DAMAGE_TAKEN_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_spell_dmg_taken", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Target)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.locNameForLangFile() + " Damage Taken")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<SpellTag> COOLDOWN_REDUCTION_PER_SPELL_TAG = DatapackStatBuilder
            .<SpellTag>of(x -> x.GUID() + "_cdr", x -> Elements.Physical)
            .addAllOfType(SpellTag.getAll())
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
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
            .setPriority(0)
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
            .setPriority(0)
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
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.INCREASE_PROJ_SPEED)
            .setLocName(x -> "Faster Projectiles")
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
            .setPriority(0)
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
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.SET_BARRAGE)
            .setLocName(x -> "Projectiles Barrage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = false;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> SUMMON_DURATION = DatapackStatBuilder
            .ofSingle("summon_duration", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
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
            .setPriority(0)
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

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_CURSED = DatapackStatBuilder
            .ofSingle("damage_to_cursed", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_CURSED)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage to Cursed Enemies")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> SUMMON_DAMAGE = DatapackStatBuilder
            .ofSingle("summon_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.summon))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Summon Damage")
            .setLocDesc(x -> "Increases damage of your summoned minions.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.icon = "\u27B9";
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> GOLEM_DAMAGE = DatapackStatBuilder
            .ofSingle("golem_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.golem))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Golem Damage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.icon = "\u27B9";
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> INCREASED_AREA = DatapackStatBuilder
            .ofSingle("inc_aoe", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addEffect(StatEffects.INCREASE_AREA)
            .setLocName(x -> "Area of Effect")
            .setLocDesc(x -> "Spell aoe effects will be larger")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> PIERCING_PROJECTILES = DatapackStatBuilder
            .ofSingle("piercing_projectiles", Elements.Physical)
            .worksWithEvent(SpellStatsCalculationEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.projectile))
            .addEffect(StatEffects.SET_PIERCE)
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
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Threat Generated")
            .setLocDesc(x -> "Modifies amount of threat you generate. Mobs attack targets with highest threat.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static DataPackStatAccessor<ModTag> EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG = DatapackStatBuilder
            .<ModTag>of(x -> "inc_effect_of_" + x.GUID() + "_buff_given", x -> Elements.Physical)
            .addAllOfType(ModTag.MAP.get(TagType.Effect))
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(e -> StatEffects.INCREASE_VALUE)
            .setLocName(x -> x.locNameForLangFile() + " Effect Strength")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                //  x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static DataPackStatAccessor<ModTag> EFFECT_OF_BUFFS_ON_YOU_PER_EFFECT_TAG = DatapackStatBuilder
            .<ModTag>of(x -> "inc_effect_of_" + x.GUID() + "_buff_on_you", x -> Elements.Physical)
            .addAllOfType(ModTag.MAP.get(TagType.Effect))
            .worksWithEvent(ExilePotionEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Target)
            .addCondition(x -> StatConditions.EFFECT_HAS_TAG.get(x))
            .addEffect(e -> StatEffects.INCREASE_VALUE)
            .setLocName(x -> Stat.VAL1 + "% to effectiveness of " + x.locNameForLangFile() + " buffs on you")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.is_perc = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> MORE_THREAT_WHEN_TAKING_DAMAGE = DatapackStatBuilder
            .ofSingle("more_threat_on_take_dmg", Elements.Physical)
            .worksWithEvent(GenerateThreatEvent.ID)
            .setPriority(0)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_THREAT_GEN_TYPE.get(ThreatGenType.take_dmg))
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> Stat.format("You generate " + Stat.VAL1 + "% more threat when taking damage."))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
                x.scaling = StatScaling.NONE;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_WHEN_LOW_HP = DatapackStatBuilder
            .ofSingle("dmg_when_low_hp", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_SOURCE_LOW_HP)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage when on Low Health")
            .setLocDesc(x -> "Low hp is 30% or less.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_WHEN_TARGET_IS_FULL_HP = DatapackStatBuilder
            .ofSingle("dmg_when_target_near_full_hp", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_NEAR_FULL_HP)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage to near Full Health Targets")
            .setLocDesc(x -> "70% health or above..")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<Elements> ELE_DAMAGE_WHEN_TARGET_IS_LOW_HP = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_dmg_when_target_low_hp", x -> x)
            .addAllOfType(Elements.getAllSingleElementals())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_LOW_HP)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> format("Execute targets bellow 30% health, dealing "
                    + VAL1 + "% increased  " + x.getIconNameFormat()))
            .setLocDesc(x -> "Low hp is 30% or less.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.is_long = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_WHEN_TARGET_IS_LOW_HP = DatapackStatBuilder
            .ofSingle("dmg_when_target_is_low_hp", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_LOW_HP)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage to Low Health Targets")
            .setLocDesc(x -> "Low hp is 30% or less.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_LIVING = DatapackStatBuilder
            .ofSingle("dmg_to_living", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_NOT_UNDEAD)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage To Living")
            .setLocDesc(x -> "Living entities are not undead ones.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_UNDEAD = DatapackStatBuilder
            .ofSingle("dmg_to_undead", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(100)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_UNDEAD)
            .addEffect(StatEffects.INCREASE_VALUE)
            .setLocName(x -> "Damage To Undead")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();

    @Override
    public void registerAll() {
        DatapackStatBuilder.STATS_TO_ADD_TO_SERIALIZATION.forEach(x -> x.addToSerializables());
    }
}
