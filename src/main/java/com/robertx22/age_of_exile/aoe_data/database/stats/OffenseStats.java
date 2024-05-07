package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.age_of_exile.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EmptyAccessor;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.test.DataPackStatAccessor;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.ChatFormatting;

import static com.robertx22.age_of_exile.database.data.stats.Stat.VAL1;
import static com.robertx22.age_of_exile.database.data.stats.Stat.format;

public class OffenseStats {

    public static DataPackStatAccessor<EmptyAccessor> CRIT_IGNORE_ELEMENTAL_RESISTS = DatapackStatBuilder
            .ofSingle("ignore_ele_res", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.BEFORE_DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ELEMENTAL)
            .addCondition(StatConditions.IS_BOOLEAN.get(EventData.CRIT))
            .addEffect(StatEffects.SET_BOOLEAN.get(EventData.RESISTED_ALREADY))
            .setLocName(x -> "Criticals Ignore Elemental Resists")
            .setLocDesc(x -> "Ignored resists means resistance stat won't proc, means penetration doesn't boost damage, but neither do enemy resists work.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.max = 1;
                x.is_long = true;
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> ARCHMAGE_BONUS_MANA_DAMAGE = DatapackStatBuilder
            .ofSingle("archmage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_FALSE.get(EventData.IS_BONUS_ELEMENT_DAMAGE))
            .addEffect(StatEffects.Layers.ADDITIVE_FLAT_DAMAGE_FROM_MANA)
            .setLocName(x -> Stat.format(
                    "Add " + VAL1 + "% of your " + Mana.getInstance().getIconNameFormat() + " to Spell Damage"
            ))
            .setLocDesc(x -> "This damage is multiplied by the damage effectiveness of the spell (The weapon damage %)")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.is_perc = true;
                x.base = 0;
                x.max = 100;
                x.min = 0;
                x.group = Stat.StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.AQUA.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> ARCHMAGE_BONUS_MANA_COST = DatapackStatBuilder
            .ofSingle("archmage_mana_cost", Elements.Physical)
            .worksWithEvent(SpendResourceEvent.ID)
            .setPriority(StatPriority.Damage.BEFORE_DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_RESOURCE.get(ResourceType.mana))
            .addEffect(StatEffects.Layers.ADDITIVE_FLAT_MANA_FROM_MANA)
            .setLocName(x -> Stat.format(
                    "Add " + VAL1 + "% of your " + Mana.getInstance().getIconNameFormat() + " to the Mana Cost"
            ))
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_long = true;
                x.is_perc = true;
                x.base = 0;
                x.max = 100;
                x.min = 0;
                x.group = Stat.StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.AQUA.getName();
            })
            .build();

    public static DataPackStatAccessor<Elements> ELEMENTAL_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> "all_" + x.guidName + "_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.dmgName + " Damage")
            .setLocDesc(x -> "Increases All dmg of that element, both spells and attacks")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = Stat.StatGroup.ELEMENTAL;
            })
            .build();
    public static DataPackStatAccessor<PlayStyle> STYLE_DAMAGE = DatapackStatBuilder
            .<PlayStyle>of(x -> x.id + "_dmg", x -> Elements.Physical)
            .addAllOfType(PlayStyle.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x.getTag()))
            .setUsesMoreMultiplier()
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.name + " Damage")
            .setLocDesc(x -> "Magic damage are spells that have tag Magic etc")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.group = Stat.StatGroup.Misc;
            })
            .build();
    public static DataPackStatAccessor<Elements> ELEMENTAL_SPELL_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> "spell_" + x.guidName + "_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(SpellTags.magic))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.dmgName + " Spells Damage")
            .setLocDesc(x -> "Increases damage of spells of that element.")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = Stat.StatGroup.ELEMENTAL;
            })
            .build();
    public static DataPackStatAccessor<WeaponTypes> WEAPON_DAMAGE = DatapackStatBuilder
            .<WeaponTypes>of(x -> x.id + "_damage", x -> Elements.Physical)
            .addAllOfType(WeaponTypes.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.WEAPON_TYPE_MATCHES.get(x))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.locName() + " Damage")
            .setLocDesc(x -> "Increases damage done if it was caused by that weapon")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = Stat.StatGroup.WEAPON;
            })
            .build();
    public static DataPackStatAccessor<WeaponTypes> ELEMENTAL_WEAPON_DAMAGE = DatapackStatBuilder
            .<WeaponTypes>of(x -> "ele_" + x.id + "_damage", x -> Elements.Physical)
            .addAllOfType(WeaponTypes.getAll())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(x -> StatConditions.WEAPON_TYPE_MATCHES.get(x))
            .addCondition(StatConditions.IS_ELEMENTAL)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Elemental " + x.locName() + " Damage")
            .setLocDesc(x -> "Increases elemental damage done if it was caused by that weapon")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = Stat.StatGroup.WEAPON;
            })
            .build();
    public static DataPackStatAccessor<Elements> ELEMENTAL_ANY_WEAPON_DAMAGE = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_any_wep_damage", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.dmgName + " Weapon Damage")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.min = 0;
                x.is_perc = true;
                x.group = Stat.StatGroup.WEAPON;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> CRIT_CHANCE = DatapackStatBuilder
            .ofSingle("critical_hit", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.FIRST)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IF_RANDOM_ROLL)
            .addEffect(StatEffects.SET_BOOLEAN.get(EventData.CRIT))
            .setLocName(x -> "Crit Chance")
            .setLocDesc(x -> "Chance to multiply attack damage by critical damage")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 1;
                x.max = 100;
                x.min = 0;
                x.group = Stat.StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.YELLOW.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> CRIT_DAMAGE = DatapackStatBuilder
            .ofSingle("critical_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_BOOLEAN.get(EventData.CRIT))
            .addEffect(StatEffects.Layers.CRIT_DAMAGE)
            .setLocName(x -> "Crit Damage")
            .setLocDesc(x -> "If Critical, multiply by x")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 100;
                x.min = 0;
                x.max = 500;
                x.group = Stat.StatGroup.MAIN;

                x.icon = "\u2694";
                x.format = ChatFormatting.RED.getName();

            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> NON_CRIT_DAMAGE = DatapackStatBuilder
            .ofSingle("non_crit_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_FALSE.get(EventData.CRIT))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Non Critical Damage")
            .setLocDesc(x -> "If not a Critical, multiply by x")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.min = -100;
                x.max = 500;
                x.group = Stat.StatGroup.MAIN;
                x.icon = "\u2694";
                x.format = ChatFormatting.RED.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> ACCURACY = DatapackStatBuilder
            .ofSingle("accuracy", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.BEFORE_HIT_PREVENTION)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.SET_ACCURACY)
            .setLocName(x -> "Accuracy")
            .setLocDesc(x -> "Increases your chance to hit, low accuracy also causes crits to fail. Specifically it decreases opponent's chance to dodge")
            .modifyAfterDone(x -> {
                x.base = 0;
                x.min = 0;
                x.scaling = StatScaling.NORMAL;
                x.group = Stat.StatGroup.MAIN;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> PROJECTILE_DAMAGE = DatapackStatBuilder
            .ofSingle("projectile_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_ANY_PROJECTILE)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Projectile Damage")
            .setLocDesc(x -> "Affects projectile damage, includes projectile spells like fireballs, and ranged basic attacks.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.icon = "\u27B9";
                x.format = ChatFormatting.RED.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> AREA_DAMAGE = DatapackStatBuilder
            .ofSingle("area_dmg", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Area Damage")
            .setLocDesc(x -> "Affects dmg done by area of effect abilities. Think meteor or other large aoe spells")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.BLUE.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> TRAP_AREA_DAMAGE = DatapackStatBuilder
            .ofSingle("trap_area_dmg", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.area))
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.trap))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Trap Area Damage")
            .setLocDesc(x -> "Affects trap dmg done by area of effect abilities.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.BLUE.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DOT_DAMAGE = DatapackStatBuilder
            .ofSingle("dot_dmg", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage Over Time")
            .setLocDesc(x -> "Increases dmg of effects that do damage over time, like burn")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> TOTAL_DAMAGE = DatapackStatBuilder
            .ofSingle("total_damage", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
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
            .ofSingle("attack_damage", Elements.NONE)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_ATTACK_DAMAGE)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
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
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.dot))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.dmgName + " Damage Over Time")
            .setLocDesc(x -> "Fire damage over time increases damage of burn, for example.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.base = 0;
                x.format = ChatFormatting.RED.getName();
                x.group = Stat.StatGroup.Misc;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DOUBLE_ATTACK_DAMAGE = DatapackStatBuilder
            .ofSingle("double_attack_chance", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.ATTACK_TYPE_MATCHES.get(AttackType.hit))
            .addEffect(StatEffects.Layers.DOUBLE_DAMAGE)
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
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(x -> StatConditions.SPELL_HAS_TAG.get(x))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> x.locNameForLangFile() + " Damage")
            .setLocDesc(x -> "Increases damage of spells with this tag. Totem Damage increases dmg of totems, etc.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_CURSED = DatapackStatBuilder
            .ofSingle("damage_to_cursed", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_CURSED)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage to Cursed Enemies")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> SUMMON_DAMAGE = DatapackStatBuilder
            .ofSingle("summon_damage", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.SPELL_HAS_TAG.get(SpellTags.summon))
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Summon Damage")
            .setLocDesc(x -> "Increases damage of your summoned minions.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
                x.icon = "\u27B9";
                x.format = ChatFormatting.RED.getName();
            })
            .build();

    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_WHEN_LOW_HP = DatapackStatBuilder
            .ofSingle("dmg_when_low_hp", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_SOURCE_LOW_HP)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage when on Low Health")
            .setLocDesc(x -> "Low hp is 30% or less.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_WHEN_TARGET_IS_FULL_HP = DatapackStatBuilder
            .ofSingle("dmg_when_target_near_full_hp", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_NEAR_FULL_HP)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage to near Full Health Targets")
            .setLocDesc(x -> "70% health or above..")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<Elements> ELE_DAMAGE_WHEN_TARGET_IS_LOW_HP = DatapackStatBuilder
            .<Elements>of(x -> x.guidName + "_dmg_when_target_low_hp", x -> x)
            .addAllOfType(Elements.values())
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_LOW_HP)
            .addCondition(StatConditions.ELEMENT_MATCH_STAT)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> format("Execute targets below 30% health, dealing "
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
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_LOW_HP)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage to Low Health Targets")
            .setLocDesc(x -> "Low hp is 30% or less.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_LIVING = DatapackStatBuilder
            .ofSingle("dmg_to_living", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setSide(EffectSides.Source)
            .setUsesMoreMultiplier()
            .addCondition(StatConditions.IS_TARGET_NOT_UNDEAD)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage To Living")
            .setLocDesc(x -> "Living entities are not undead ones.")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();
    public static DataPackStatAccessor<EmptyAccessor> DAMAGE_TO_UNDEAD = DatapackStatBuilder
            .ofSingle("dmg_to_undead", Elements.Physical)
            .worksWithEvent(DamageEvent.ID)
            .setPriority(StatPriority.Damage.DAMAGE_LAYERS)
            .setUsesMoreMultiplier()
            .setSide(EffectSides.Source)
            .addCondition(StatConditions.IS_TARGET_UNDEAD)
            .addEffect(StatEffects.Layers.ADDITIVE_DAMAGE_PERCENT)
            .setLocName(x -> "Damage To Undead")
            .setLocDesc(x -> "")
            .modifyAfterDone(x -> {
                x.is_perc = true;
            })
            .build();


    public static void init() {

    }
}
