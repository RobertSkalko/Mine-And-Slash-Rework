package com.robertx22.age_of_exile.aoe_data.database.stat_conditions;

import com.robertx22.age_of_exile.aoe_data.DataHolder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.ThreatGenType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition.*;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StatConditions implements ExileRegistryInit {

    public static StatCondition IF_CRIT = new IsBooleanTrueCondition(EventData.CRIT);
    public static StatCondition IF_NOT_CRIT = new IsBooleanTrueCondition(EventData.CRIT).flipCondition();
    public static StatCondition IF_RANDOM_ROLL = new RandomRollCondition();
    public static StatCondition IS_SPELL = new IsSpellCondition();
    public static StatCondition ELEMENT_MATCH_STAT = new ElementMatchesStat();
    public static StatCondition IS_DAY = new IsDayCondition();
    public static StatCondition IS_NIGHT = new IsDayCondition().flipCondition();
    public static StatCondition IS_TARGET_UNDEAD = new IsUndeadCondition();
    public static StatCondition IS_TARGET_CURSED = new IsTargetCursed();
    public static StatCondition IS_TARGET_NOT_UNDEAD = new IsUndeadCondition().flipCondition();
    public static StatCondition IS_IN_COMBAT = new IsInCombatCondition();
    public static StatCondition IS_NOT_IN_COMBAT = new IsInCombatCondition().flipCondition();
    public static StatCondition IS_BASIC_ATTACK = new IsBooleanTrueCondition(EventData.IS_BASIC_ATTACK);
    public static StatCondition IS_TARGET_LOW_HP = new IsHealthBellowPercentCondition("is_target_low_hp", 30, EffectSides.Target);
    public static StatCondition IS_TARGET_LOW_MAGIC_SHIELD = new IsMSBellowPercentCondition("is_target_low_magic_shield", 30, EffectSides.Target);
    public static StatCondition IS_TARGET_LOW = new IsTargetLow("is_target_low", 30, EffectSides.Target);
    public static StatCondition IS_SOURCE_LOW_HP = new IsHealthBellowPercentCondition("is_source_low_hp", 30, EffectSides.Source);
    public static StatCondition IS_TARGET_NEAR_FULL_HP = new IsHealthAbovePercentCondition("is_target_near_full_hp", 70, EffectSides.Target);
    public static StatCondition IS_ELEMENTAL = new StringMatchesCondition(EventData.ELEMENT, Elements.Physical.name()).flipCondition();
    public static StatCondition IS_ATTACK_DAMAGE = new StringMatchesCondition(EventData.STYLE, PlayStyle.INT.id).flipCondition();
    public static StatCondition IS_NOT_SUMMON_ATTACK = new IsBooleanTrueCondition(EventData.IS_SUMMON_ATTACK).flipCondition();


    public static DataHolder<EffectTags, StatCondition> EFFECT_HAS_TAG = new DataHolder<>(
            EffectTags.values()
            , x -> new EffectHasTagCondition(x));

    public static DataHolder<ResourceType, StatCondition> IS_RESOURCE = new DataHolder<>(
            ResourceType.values()
            , x -> new StringMatchesCondition(EventData.RESOURCE_TYPE, x.name()));

    public static DataHolder<ThreatGenType, StatCondition> IS_THREAT_GEN_TYPE = new DataHolder<>(
            ThreatGenType.values()
            , x -> new StringMatchesCondition(EventData.THREAT_GEN_TYPE, x.name()));

    public static DataHolder<SpellTag, StatCondition> SPELL_HAS_TAG = new DataHolder<>(
            SpellTag.values()
            , x -> new SpellHasTagCondition(x));


    public static DataHolder<SummonType, StatCondition> IS_SUMMON_TYPE = new DataHolder<>(
            SummonType.values()
            , x -> new StringMatchesCondition(EventData.SUMMON_TYPE, x.id));

    public static DataHolder<RestoreType, StatCondition> IS_RESTORE_TYPE = new DataHolder<>(
            RestoreType.values()
            , x -> new StringMatchesCondition(EventData.RESTORE_TYPE, x.name()));

    public static StatCondition IS_MELEE_WEAPON = new EitherIsTrueCondition("is_melee_weapon",
            WeaponTypes.getAll().stream()
                    .filter(x -> x.isMelee())
                    .map(x -> new WeaponTypeMatches(x).GUID())
                    .collect(Collectors.toList())
    );

    public static StatCondition IS_MAGIC_WEAPON = new EitherIsTrueCondition("is_magic_weapon",
            WeaponTypes.getAll().stream()
                    .filter(x -> x.style == PlayStyle.INT)
                    .map(x -> new WeaponTypeMatches(x).GUID())
                    .collect(Collectors.toList())
    );

    public static StatCondition IS_RANGED_WEAPON = new EitherIsTrueCondition("is_ranged_weapon",
            Arrays.asList(
                    new WeaponTypeMatches(WeaponTypes.crossbow).GUID(),
                    new WeaponTypeMatches(WeaponTypes.bow).GUID()
            ));


    public static StatCondition IS_ANY_PROJECTILE = new EitherIsTrueCondition("is_projectile",
            Arrays.asList(SPELL_HAS_TAG.get(SpellTag.projectile).GUID(), IS_RANGED_WEAPON.GUID()));

    public static DataHolder<AttackType, StatCondition> ATTACK_TYPE_MATCHES = new DataHolder<>(
            AttackType.values()
            , x -> new StringMatchesCondition(EventData.ATTACK_TYPE, x.name()));

    public static DataHolder<WeaponTypes, StatCondition> WEAPON_TYPE_MATCHES = new DataHolder<>(
            WeaponTypes.getAll()
            , x -> new WeaponTypeMatches(x));

    // todo
    public static StatCondition IS_ATTACK_OR_SPELL_ATTACK = new EitherIsTrueCondition(
            "is_attack_or_spell_attack",
            Arrays.asList(ATTACK_TYPE_MATCHES.get(AttackType.hit).GUID())
    );


    public static void loadClass() {
    }

    @Override
    public void registerAll() {

        ATTACK_TYPE_MATCHES.addToSerializables();
        IS_TARGET_LOW.addToSerializables();
        IS_TARGET_LOW_MAGIC_SHIELD.addToSerializables();
        IS_NOT_SUMMON_ATTACK.addToSerializables();
        IS_TARGET_CURSED.addToSerializables();
        IS_SUMMON_TYPE.addToSerializables();
        IS_NOT_IN_COMBAT.addToSerializables();
        IF_CRIT.addToSerializables();
        IF_RANDOM_ROLL.addToSerializables();
        IS_SPELL.addToSerializables();
        WEAPON_TYPE_MATCHES.addToSerializables();
        IF_NOT_CRIT.addToSerializables();
        ELEMENT_MATCH_STAT.addToSerializables();
        IS_ATTACK_OR_SPELL_ATTACK.addToSerializables();
        IS_DAY.addToSerializables();
        IS_NIGHT.addToSerializables();
        IS_BASIC_ATTACK.addToSerializables();
        IS_RANGED_WEAPON.addToSerializables();
        IS_ANY_PROJECTILE.addToSerializables();
        IS_MAGIC_WEAPON.addToSerializables();
        IS_MELEE_WEAPON.addToSerializables();
        SPELL_HAS_TAG.addToSerializables();
        IS_ATTACK_DAMAGE.addToSerializables();
        IS_RESOURCE.addToSerializables();
        IS_IN_COMBAT.addToSerializables();
        IS_RESTORE_TYPE.addToSerializables();
        EFFECT_HAS_TAG.addToSerializables();
        IS_THREAT_GEN_TYPE.addToSerializables();
        IS_TARGET_LOW_HP.addToSerializables();
        IS_SOURCE_LOW_HP.addToSerializables();
        IS_TARGET_UNDEAD.addToSerializables();
        IS_TARGET_NOT_UNDEAD.addToSerializables();

    }
}
