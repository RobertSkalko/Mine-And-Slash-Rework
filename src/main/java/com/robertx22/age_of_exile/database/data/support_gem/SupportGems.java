package com.robertx22.age_of_exile.database.data.support_gem;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.Arrays;

public class SupportGems {

    static String PROJ_COUNT = "proj_count";

    public static void init() {

        new SupportGem("gmp_barrage", "Greater Barrage Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-65, -65, Stats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(4, 4, Stats.PROJECTILE_COUNT.get(), ModType.FLAT),
                        new StatMod(1, 1, Stats.PROJECTILE_BARRAGE.get(), ModType.FLAT)
                ))
                .levelReq(30)
                .setOneOfAKind(PROJ_COUNT)
                .addToSerializables();

        new SupportGem("gmp", "Greater Multiple Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-65, -65, Stats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(4, 4, Stats.PROJECTILE_COUNT.get(), ModType.FLAT)
                ))
                .levelReq(30)
                .setOneOfAKind(PROJ_COUNT)
                .addToSerializables();

        new SupportGem("lmp_barrage", "Lesser Barrage Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-50, -50, Stats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(10, 30, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(2, 2, Stats.PROJECTILE_COUNT.get(), ModType.FLAT),
                        new StatMod(1, 1, Stats.PROJECTILE_BARRAGE.get(), ModType.FLAT)
                ))
                .levelReq(10)
                .setOneOfAKind(PROJ_COUNT).addToSerializables();

        new SupportGem("lmp", "Lesser Multiple Projectiles", PlayStyle.DEX, 1.5F,
                Arrays.asList(
                        new StatMod(-50, -50, Stats.TOTAL_DAMAGE.get(), ModType.MORE),
                        new StatMod(10, 30, Stats.PROJECTILE_DAMAGE.get(), ModType.FLAT),
                        new StatMod(2, 2, Stats.PROJECTILE_COUNT.get(), ModType.FLAT)
                ))
                .levelReq(1)
                .setOneOfAKind(PROJ_COUNT).addToSerializables();
        // wacky

        new SupportGem("proj_speed", "Faster Projectiles", PlayStyle.DEX, 1.1F,
                Arrays.asList(
                        new StatMod(10, 25, Stats.PROJECTILE_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 50, Stats.PROJECTILE_SPEED.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();


        new SupportGem("small_cdr", "Minor Cooldown", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(5, 15, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("inc_heal", "Increased Healing", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(20, 40, Stats.HEAL_STRENGTH.get(), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("heal_at_low", "Immediate Care", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(25, 50, Stats.LOW_HP_HEALING.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("totem_dmg_cdr", "Primed Totems", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(5, 10, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.MORE),
                        new StatMod(10, 20, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("totem_damage", "Totem Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        for (ResourceType res : ResourceType.getUsed()) {
            new SupportGem(res.id + "_on_hit", res.locname + " On Hit", PlayStyle.DEX, 1.2F,
                    Arrays.asList(new StatMod(1, 3, Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(res, AttackType.hit)), ModType.FLAT)
                    ))
                    .levelReq(1).addToSerializables();
        }

        for (Elements ele : Elements.getAllSingle()) {
            new SupportGem(ele.guidName + "_pene", ele.dmgName + " Penetration", PlayStyle.STR, 1.25F,
                    Arrays.asList(new StatMod(15, 30, new ElementalPenetration(ele), ModType.FLAT)
                    ))
                    .levelReq(10).addToSerializables();
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
                Arrays.asList(new StatMod(15, 40, Stats.SUMMON_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("dot_damage", "Damage over Time", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.DOT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("spell_damage", "Spell Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE)
                ))
                .levelReq(30).addToSerializables();

        new SupportGem("melee_damage", "Melee Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 35, Stats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("ranged_damage", "Ranged Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 35, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("aoe_dmg", "Area Focus", PlayStyle.INT, 1.3F,
                Arrays.asList(
                        new StatMod(15, 35, Stats.AREA_DAMAGE.get(), ModType.MORE),
                        new StatMod(-20, -50, Stats.INCREASED_AREA.get(), ModType.FLAT)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("plus_aoe", "Expanded Area", PlayStyle.STR, 1.2F,
                Arrays.asList(
                        new StatMod(-10, -25, Stats.AREA_DAMAGE.get(), ModType.MORE),
                        new StatMod(20, 40, Stats.INCREASED_AREA.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("cold_damage", "Cold Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("fire_damage", "Fire Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("lightning_damage", "Lightning Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("chaos_damage", "Chaos Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();
        new SupportGem("physical_damage", "Physical Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        // stronger conditional dmg multis
        new SupportGem("ailment_damage_less_hit_dmg", "Ailment Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, AllAilmentDamage.getInstance(), ModType.MORE),
                        new StatMod(-10, -10, HitDamage.getInstance(), ModType.MORE)
                ))
                .levelReq(20).addToSerializables();

        new SupportGem("spell_damage_no_crit", "Restrained Destruction", PlayStyle.DEX, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE),
                        new StatMod(-100, -100, Stats.CRIT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("crit_dmg_less_non_crit_dmg", "Confident Ruin", PlayStyle.STR, 1.3F,
                Arrays.asList(
                        new StatMod(25, 125, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
                        new StatMod(-80, -80, Stats.NON_CRIT_DAMAGE.get(), ModType.MORE)
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


        // weaker
        new SupportGem("crit_chance", "Crit Chance", PlayStyle.DEX, 1.2F,
                Arrays.asList(new StatMod(10, 30, Stats.CRIT_CHANCE.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();

        new SupportGem("crit_damage", "Crit Damage", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 100, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
                ))
                .levelReq(10).addToSerializables();

        new SupportGem("mana_saver_dmg", "Mana Conservation", PlayStyle.STR, 0.8F,
                Arrays.asList(new StatMod(10, 25, Stats.NON_CRIT_DAMAGE.get(), ModType.MORE)
                ))
                .levelReq(10).addToSerializables();


        // durations

        new SupportGem("summon_duration", "Summon Duration", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(10, 50, Stats.SUMMON_DURATION.get(), ModType.FLAT)
                ))
                .levelReq(1).addToSerializables();


        // exceptional
        new SupportGem("rare_cooldown", "Greater Cooldown", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 50, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
                )).edit(x -> {
            x.weight = 50;
        })
                .levelReq(30).addToSerializables();

        new SupportGem("rare_mana_eff", "Greater Mana Cost", PlayStyle.STR, 1,
                Arrays.asList(new StatMod(-15, -50, Stats.MANA_COST.get(), ModType.FLAT)
                )).edit(x -> {
            x.weight = 50;
        })
                .levelReq(30).addToSerializables();


    }
}
