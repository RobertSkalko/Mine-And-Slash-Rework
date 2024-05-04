package com.robertx22.age_of_exile.database.data.support_gem;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.EffectStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentProcStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AllAilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.HitDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusFlatElementalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.summon.SummonHealth;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.Arrays;

public class SupportGems {

    static String PROJ_COUNT = "proj_count";

    public static void init() {

        new SupportGem("archmage", "Archmage", PlayStyle.INT, 1.25F,
                Arrays.asList(
                        OffenseStats.ARCHMAGE_BONUS_MANA_DAMAGE.get().mod(3, 15),
                        OffenseStats.ARCHMAGE_BONUS_MANA_COST.get().mod(1, 5)
                ))
                .levelReq(10).addToSerializables();


        new SupportGem("gmp_barrage", "Greater Barrage Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-65, -65, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(4, 4, SpellChangeStats.PROJECTILE_COUNT.get(), ModType.FLAT),
                        new StatMod(1, 1, SpellChangeStats.PROJECTILE_BARRAGE.get(), ModType.FLAT)
                ))
                .levelReq(30)
                .setOneOfAKind(PROJ_COUNT)
                .addToSerializables();

        new SupportGem("gmp", "Greater Multiple Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-65, -65, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(4, 4, SpellChangeStats.PROJECTILE_COUNT.get(), ModType.FLAT)
                ))
                .levelReq(30)
                .setOneOfAKind(PROJ_COUNT)
                .addToSerializables();

        new SupportGem("lmp_barrage", "Lesser Barrage Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-50, -50, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(10, 30, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(2, 2, SpellChangeStats.PROJECTILE_COUNT.get(), ModType.FLAT),
                        new StatMod(1, 1, SpellChangeStats.PROJECTILE_BARRAGE.get(), ModType.FLAT)
                ))
                .levelReq(10)
                .setOneOfAKind(PROJ_COUNT).addToSerializables();

        new SupportGem("lmp", "Lesser Multiple Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-50, -50, OffenseStats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(10, 30, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(2, 2, SpellChangeStats.PROJECTILE_COUNT.get(), ModType.FLAT)
                ))
                .levelReq(1)
                .setOneOfAKind(PROJ_COUNT).addToSerializables();
        // wacky

        new SupportGem("proj_speed", "Faster Projectiles", PlayStyle.DEX, 1.1F,
                Arrays.asList(
                        new StatMod(10, 25, OffenseStats.PROJECTILE_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, SpellChangeStats.PROJECTILE_SPEED.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();


        new SupportGem("small_cdr", "Minor Cooldown", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(5, 15, SpellChangeStats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("inc_heal", "Increased Healing", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(20, 40, ResourceStats.HEAL_STRENGTH.get(), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("heal_at_low", "Immediate Care", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(25, 50, ResourceStats.LOW_HP_HEALING.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("totem_dmg_cdr", "Primed Totems", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(5, 10, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE),
                        new StatMod(10, 20, SpellChangeStats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.totem), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("totem_damage", "Totem Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.totem), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        for (ResourceType res : ResourceType.getUsed()) {
            new SupportGem(res.id + "_on_hit", res.locname + " On Hit", PlayStyle.DEX, 1.2F,
                    Arrays.asList(new StatMod(1, 3, ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(res, AttackType.hit)), ModType.FLAT)
                    ))
                    .levelReq(1).addToSerializables();
        }

        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                new SupportGem(ele.guidName + "_pene", ele.dmgName + " Penetration", PlayStyle.STR, 1.25F,
                        Arrays.asList(new StatMod(15, 30, new ElementalPenetration(ele), ModType.FLAT)
                        ))
                        .levelReq(10).addToSerializables();

                new SupportGem(ele.guidName + "_flat_dmg", ele.dmgName + " Flat Damage", PlayStyle.INT, 1.1F,
                        Arrays.asList(new BonusFlatElementalDamage(ele).mod(6, 8)
                        ))
                        .levelReq(5).addToSerializables();
            }
        }

        new SupportGem("proc_freeze", "Ice Breaker", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(25, 100, new AilmentProcStat(Ailments.FREEZE), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("proc_shock", "Shock", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(25, 100, new AilmentProcStat(Ailments.ELECTRIFY), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        // damage multipliers


        new SupportGem("summon_damage", "Summon Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 40, OffenseStats.SUMMON_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("dot_damage", "Damage Over Time", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.DOT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("spell_damage", "Spell Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE)
                ))
                .levelReq(30).addToSerializables();

        new SupportGem("melee_damage", "Melee Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 35, OffenseStats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("ranged_damage", "Ranged Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 35, OffenseStats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("aoe_dmg", "Area Focus", PlayStyle.INT, 1.3F,
                Arrays.asList(
                        new StatMod(15, 35, OffenseStats.AREA_DAMAGE.get(), ModType.MORE),
                        new StatMod(-20, -50, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("plus_aoe", "Expanded Area", PlayStyle.INT, 1.2F,
                Arrays.asList(
                        new StatMod(10, 25, OffenseStats.AREA_DAMAGE.get(), ModType.FLAT),
                        new StatMod(20, 40, SpellChangeStats.INCREASED_AREA.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("cold_damage", "Cold Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("fire_damage", "Fire Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("lightning_damage", "Lightning Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("chaos_damage", "Chaos Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("physical_damage", "Physical Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        // stronger conditional dmg multis
        new SupportGem("ailment_damage_less_hit_dmg", "Ailment Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, AllAilmentDamage.getInstance(), ModType.MORE),
                        new StatMod(-10, -10, HitDamage.getInstance(), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("spell_damage_no_crit", "Restrained Destruction", PlayStyle.INT, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, OffenseStats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE),
                        new StatMod(-100, -100, OffenseStats.CRIT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("crit_dmg_less_non_crit_dmg", "Confident Ruin", PlayStyle.STR, 1.3F,
                Arrays.asList(
                        new StatMod(25, 125, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT),
                        new StatMod(-80, -80, OffenseStats.NON_CRIT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(30).addToSerializables();


        // ailment chances
        new SupportGem("poison_chance", "Poison Chance", PlayStyle.DEX, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.POISON), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("burn_chance", "Burn Chance", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.BURN), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("freeze_chance", "Freeze Chance", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.FREEZE), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();


        new SupportGem("bleed_chance", "Bleed Chance", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.BLEED), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("electrify_chance", "Electrify Chance", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();


        new SupportGem("summon_health", "Summon Health", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(20, 50, SummonHealth.getInstance(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        // weaker
        new SupportGem("crit_chance", "Crit Chance", PlayStyle.DEX, 1.2F,
                Arrays.asList(new StatMod(10, 30, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("crit_damage", "Crit Damage", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 100, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("mana_saver_dmg", "Mana Conservation", PlayStyle.INT, 0.8F,
                Arrays.asList(new StatMod(10, 25, OffenseStats.NON_CRIT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();


        // durations

        new SupportGem("summon_duration", "Summon Duration", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(10, 50, SpellChangeStats.SUMMON_DURATION.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("effect_duration", "Effect Duration", PlayStyle.INT, 1.15F,
                Arrays.asList(new StatMod(15, 40, EffectStats.EFFECT_DURATION_YOU_CAST.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();


        // exceptional
        new SupportGem("rare_cooldown", "Greater Cooldown", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 50, SpellChangeStats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
                )).edit(x -> {
                    x.weight = 50;
                })
                .levelReq(30).addToSerializables();

        new SupportGem("rare_mana_eff", "Greater Mana Cost", PlayStyle.STR, 1,
                Arrays.asList(new StatMod(-15, -50, SpellChangeStats.MANA_COST.get(), ModType.FLAT)
                )).edit(x -> {
                    x.weight = 50;
                })
                .levelReq(30).addToSerializables();


        new SupportGem("threat", "Greater Threat", PlayStyle.STR, 0.95F,
                Arrays.asList(
                        new StatMod(25, 50, SpellChangeStats.THREAT_GENERATED.get(), ModType.MORE),
                        new StatMod(20, 50, OffenseStats.TOTAL_DAMAGE.get(), ModType.FLAT)
                ))
                .levelReq(5).addToSerializables();

        new SupportGem("minion_threat", "Minion Threat", PlayStyle.STR, 0.95F,
                Arrays.asList(
                        new StatMod(25, 50, SpellChangeStats.THREAT_GENERATED.get(), ModType.MORE),
                        new StatMod(25, 100, SummonHealth.getInstance(), ModType.MORE)
                ))
                .levelReq(5).addToSerializables();

        new SupportGem("aggressive_minions", "Aggressive Minions", PlayStyle.STR, 0.95F,
                Arrays.asList(
                        new StatMod(25, 50, SpellChangeStats.AGGRO_RADIUS.get(), ModType.MORE),
                        new StatMod(15, 30, OffenseStats.SUMMON_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(5).addToSerializables();


        new SupportGem("defensive_minions", "Defensive Minions", PlayStyle.STR, 0.95F,
                Arrays.asList(
                        new StatMod(-25, -50, SpellChangeStats.AGGRO_RADIUS.get(), ModType.MORE),
                        new StatMod(25, 50, SummonHealth.getInstance(), ModType.MORE)
                ))
                .levelReq(5).addToSerializables();

    }
}
