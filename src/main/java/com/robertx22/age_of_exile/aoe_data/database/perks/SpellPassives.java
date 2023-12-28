package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.BlockChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.database.data.stats.types.summon.GolemSpellChance;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellPassives implements ExileRegistryInit {

    public static String BURN_CHANCE = "p_burn_chance";
    public static String FREEZE_CHANCE = "p_freeze_chance";
    public static String SPELL_DMG = "p_spell_dmg";
    public static String MANA_REGEN = "p_mana_regen";
    public static String MAGIC_SHIELD_REGEN = "p_magic_shield_regen";
    public static String CAST_SPEED_SORC = "p_cast_speed_sorc";
    public static String ELE_RES = "p_ele_res";
    public static String GOLEM_CHANCE = "p_golem_chance";

    public static String POISON_CHANCE = "p_poison_chance";
    public static String POISON_DURATION = "p_poison_duration";
    public static String DOT_DMG = "p_dot_dmg";
    public static String CAST_SPEED_WL = "p_cast_speed_wl";
    public static String SUMMON_DMG = "p_summon_dmg";
    public static String DMG_TO_CURSED = "p_dmg_to_cursed";
    public static String SPELL_LIFESTEAL = "p_spell_lifesteal";
    public static String DOT_LIFESTEAL = "p_dot_lifesteal";

    public static String HEALTH_MINS = "p_health_mins";
    public static String SONG_DURATION = "p_song_duration";
    public static String INC_AOE = "p_inc_aoe";
    public static String HEAL_STR = "p_heal_str";
    public static String LOW_HP_HEAL = "p_low_hp_heal";
    public static String MANA_COST = "p_mana_cost";
    public static String CDR = "p_cdr";
    public static String HEAL_TO_SPELL = "p_heal_to_spell";

    public static String PROJ_DMG = "p_proj_dmg";
    public static String PROJ_SPD = "p_proj_spd";
    public static String DODGE = "p_dodge";
    public static String MOVE_SPEED = "p_move_speed";
    public static String CRIT_DMG = "p_crit_dmg";
    public static String SUMMON_HEALTH = "p_summon_health";
    public static String ENE_REGEN = "p_ene_regen";
    public static String TRAP_DMG = "p_trap_dmg";
    public static String TRAP_CDR = "p_trap_cdr";

    public static String HEALTH_SHA = "p_health_sha";
    public static String MANA_SHA = "p_mana_sha";
    public static String LIGHTNING_ELE = "p_lightning_ele";
    public static String ARMOR_PER_MANA = "p_armor_per_mana";
    public static String ELECTRIFY_CHANCE = "p_electrify_chance";
    public static String TOTEM_DMG = "p_totem_dmg";
    public static String TOTEM_CDR = "p_totem_cdr";
    public static String ELE_PEN = "p_ele_pen";

    public static String ARMOR_PEN = "p_armor_pen";
    public static String HEALTH_REGEN = "p_health_regen";
    public static String HEALTH_WAR = "p_health_war";
    public static String CRIT_HIT = "p_crit_hit";
    public static String DAMAGE_RECEIVED = "p_damage_received";
    public static String AURA_EFFECT = "p_aura_effect";
    public static String BLOCK_CHANCE = "p_block_chance";
    public static String LIFESTEAL = "p_lifesteal";

    @Override
    public void registerAll() {

        // Sorcerer
        PerkBuilder.passive(FREEZE_CHANCE, 8, new OptScaleExactStat(4, new AilmentChance(Ailments.FREEZE)));
        PerkBuilder.passive(BURN_CHANCE, 8, new OptScaleExactStat(4, new AilmentChance(Ailments.BURN)));
        PerkBuilder.passive(MANA_REGEN, 8, new OptScaleExactStat(4, ManaRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(SPELL_DMG, 8, new OptScaleExactStat(2, SkillDamage.getInstance(), ModType.FLAT));
        PerkBuilder.passive(MAGIC_SHIELD_REGEN, 8, new OptScaleExactStat(5, MagicShieldRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(CAST_SPEED_SORC, 8, new OptScaleExactStat(3, Stats.CAST_SPEED.get(), ModType.FLAT));
        PerkBuilder.passive(ELE_RES, 8, new OptScaleExactStat(4, new ElementalResist(Elements.Elemental)));
        PerkBuilder.passive(GOLEM_CHANCE, 8, new OptScaleExactStat(3, GolemSpellChance.getInstance(), ModType.FLAT));

        // Warlock
        PerkBuilder.passive(POISON_CHANCE, 8, new OptScaleExactStat(4, new AilmentChance(Ailments.POISON)));
        PerkBuilder.passive(POISON_DURATION, 8, new OptScaleExactStat(6, new AilmentDuration(Ailments.POISON)));
        PerkBuilder.passive(DOT_DMG, 8, new OptScaleExactStat(3, Stats.DOT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.passive(CAST_SPEED_WL, 8, new OptScaleExactStat(2, Stats.CAST_SPEED.get(), ModType.FLAT));
        PerkBuilder.passive(SUMMON_DMG, 8, new OptScaleExactStat(2, Stats.SUMMON_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.passive(DMG_TO_CURSED, 8, new OptScaleExactStat(4, Stats.DAMAGE_TO_CURSED.get(), ModType.FLAT));
        PerkBuilder.passive(SPELL_LIFESTEAL, 8, new OptScaleExactStat(1, Stats.SPELL_LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.passive(DOT_LIFESTEAL, 8, new OptScaleExactStat(2, Stats.DOT_LIFESTEAL.get(), ModType.FLAT));

        // Minstrel
        PerkBuilder.passive(HEALTH_MINS, 8, new OptScaleExactStat(3, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(SONG_DURATION, 8, new OptScaleExactStat(4, Stats.EFFECT_DURATION_YOU_CAST_PER_TAG.get(EffectTags.song)));
        PerkBuilder.passive(INC_AOE, 8, new OptScaleExactStat(3, Stats.INCREASED_AREA.get()));
        PerkBuilder.passive(HEAL_STR, 8, new OptScaleExactStat(2, Stats.HEAL_STRENGTH.get()));
        PerkBuilder.passive(LOW_HP_HEAL, 8, new OptScaleExactStat(4, Stats.LOW_HP_HEALING.get()));
        PerkBuilder.passive(MANA_COST, 8, new OptScaleExactStat(3, Stats.MANA_COST.get()));
        PerkBuilder.passive(CDR, 8, new OptScaleExactStat(2, Stats.COOLDOWN_REDUCTION.get()));
        PerkBuilder.passive(HEAL_TO_SPELL, 8, new OptScaleExactStat(10, DatapackStats.HEAL_TO_SPELL_DMG));

        // Hunter
        PerkBuilder.passive(PROJ_DMG, 8, new OptScaleExactStat(2, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.passive(PROJ_SPD, 8, new OptScaleExactStat(3, Stats.PROJECTILE_SPEED.get(), ModType.FLAT));
        PerkBuilder.passive(DODGE, 8, new OptScaleExactStat(4, DodgeRating.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(CRIT_DMG, 8, new OptScaleExactStat(4, Stats.CRIT_DAMAGE.get()));
        PerkBuilder.passive(SUMMON_HEALTH, 8, new OptScaleExactStat(4, SummonHealth.getInstance(), ModType.FLAT));
        PerkBuilder.passive(ENE_REGEN, 8, new OptScaleExactStat(4, EnergyRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(TRAP_DMG, 8, new OptScaleExactStat(5, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap), ModType.FLAT));
        PerkBuilder.passive(TRAP_CDR, 8, new OptScaleExactStat(3, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.trap), ModType.FLAT));

        // Shaman
        PerkBuilder.passive(HEALTH_SHA, 8, new OptScaleExactStat(3, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(MANA_SHA, 8, new OptScaleExactStat(3, Mana.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(ELE_PEN, 8, new OptScaleExactStat(2, new ElementalPenetration(Elements.Elemental)));
        PerkBuilder.passive(LIGHTNING_ELE, 8, new OptScaleExactStat(2, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning)));
        PerkBuilder.passive(ARMOR_PER_MANA, 8, new OptScaleExactStat(0.2f, DatapackStats.ARMOR_PER_MANA));
        PerkBuilder.passive(ELECTRIFY_CHANCE, 8, new OptScaleExactStat(4, new AilmentChance(Ailments.ELECTRIFY)));
        PerkBuilder.passive(TOTEM_DMG, 8, new OptScaleExactStat(5, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.FLAT));
        PerkBuilder.passive(TOTEM_CDR, 8, new OptScaleExactStat(3, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.totem), ModType.FLAT));

        // Warrior
        PerkBuilder.passive(HEALTH_WAR, 8, new OptScaleExactStat(4, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(HEALTH_REGEN, 8, new OptScaleExactStat(4, HealthRegen.getInstance(), ModType.PERCENT));
        PerkBuilder.passive(CRIT_HIT, 8, new OptScaleExactStat(1, Stats.CRIT_CHANCE.get()));
        PerkBuilder.passive(DAMAGE_RECEIVED, 8, new OptScaleExactStat(-1.5f, Stats.DAMAGE_RECEIVED.get()));
        PerkBuilder.passive(AURA_EFFECT, 8, new OptScaleExactStat(2, AuraEffect.getInstance(), ModType.FLAT));
        PerkBuilder.passive(BLOCK_CHANCE, 8, new OptScaleExactStat(1, BlockChance.getInstance(), ModType.FLAT));
        PerkBuilder.passive(LIFESTEAL, 8, new OptScaleExactStat(1, Stats.LIFESTEAL.get(), ModType.FLAT));
        PerkBuilder.passive(ARMOR_PEN, 8, new OptScaleExactStat(3, ArmorPenetration.getInstance()));
    }
}
