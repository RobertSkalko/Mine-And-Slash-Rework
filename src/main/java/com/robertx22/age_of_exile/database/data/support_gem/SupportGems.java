package com.robertx22.age_of_exile.database.data.support_gem;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AllAilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.HitDamage;
import com.robertx22.age_of_exile.database.data.stats.types.totem.ProjectileTotem;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.Arrays;

public class SupportGems {


    public static void init() {

        // custom functionality
        new SupportGem("projectile_totem", "Projectile Totem", PlayStyle.STR, 3,
                Arrays.asList(new StatMod(1, 1, ProjectileTotem.getInstance(), ModType.FLAT)
                )).registerToExileRegistry();
        // todo reduce dmg


        // damage multipliers
        new SupportGem("dot_damage", "Damage over Time", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.DOT_DAMAGE.get(), ModType.MORE)
                )).registerToExileRegistry();

        new SupportGem("spell_damage", "Spell Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE)
                )).registerToExileRegistry();

        new SupportGem("melee_damage", "Melee Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 35, Stats.STYLE_DAMAGE.get(PlayStyle.STR), ModType.MORE)
                )).registerToExileRegistry();

        new SupportGem("ranged_damage", "Ranged Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 35, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.MORE)
                )).registerToExileRegistry();


        new SupportGem("cold_damage", "Cold Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE)
                )).registerToExileRegistry();
        new SupportGem("fire_damage", "Fire Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.MORE)
                )).registerToExileRegistry();
        new SupportGem("lightning_damage", "Lightning Damage", PlayStyle.INT, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning), ModType.MORE)
                )).registerToExileRegistry();
        new SupportGem("chaos_damage", "Chaos Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.MORE)
                )).registerToExileRegistry();
        new SupportGem("physical_damage", "Physical Damage", PlayStyle.STR, 1.3F,
                Arrays.asList(new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE)
                )).registerToExileRegistry();

        // stronger conditional dmg multis
        new SupportGem("ailment_damage_less_hit_dmg", "Ailment Damage", PlayStyle.DEX, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, AllAilmentDamage.getInstance(), ModType.MORE),
                        new StatMod(-75, -75, HitDamage.getInstance(), ModType.MORE)
                )).registerToExileRegistry();

        new SupportGem("spell_damage_no_crit", "Restrained Destruction", PlayStyle.DEX, 1.3F,
                Arrays.asList(
                        new StatMod(20, 45, Stats.STYLE_DAMAGE.get(PlayStyle.INT), ModType.MORE),
                        new StatMod(-100, -100, Stats.CRIT_DAMAGE.get(), ModType.MORE)
                )).registerToExileRegistry();

        new SupportGem("crit_dmg_less_non_crit_dmg", "Confident Ruin", PlayStyle.STR, 1.3F,
                Arrays.asList(
                        new StatMod(25, 125, Stats.CRIT_DAMAGE.get(), ModType.FLAT),
                        new StatMod(-80, -80, Stats.NON_CRIT_DAMAGE.get(), ModType.MORE)
                )).registerToExileRegistry();


        // ailment chances
        new SupportGem("poison_chance", "Poison Chance", PlayStyle.DEX, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.POISON), ModType.FLAT)
                )).registerToExileRegistry();

        new SupportGem("burn_chance", "Burn Chance", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.BURN), ModType.FLAT)
                )).registerToExileRegistry();

        new SupportGem("freeze_chance", "Freeze Chance", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.FREEZE), ModType.FLAT)
                )).registerToExileRegistry();


        new SupportGem("bleed_chance", "Bleed Chance", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.BLEED), ModType.FLAT)
                )).registerToExileRegistry();

        new SupportGem("electrify_chance", "Electrify Chance", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 40, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT)
                )).registerToExileRegistry();


        // weaker
        new SupportGem("crit_chance", "Crit Chance", PlayStyle.DEX, 1.2F,
                Arrays.asList(new StatMod(10, 30, Stats.CRIT_CHANCE.get(), ModType.FLAT)
                )).registerToExileRegistry();

        new SupportGem("crit_damage", "Crit Damage", PlayStyle.STR, 1.2F,
                Arrays.asList(new StatMod(15, 100, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
                )).registerToExileRegistry();

        new SupportGem("mana_saver_dmg", "Mana Conservation", PlayStyle.STR, 0.75F,
                Arrays.asList(new StatMod(10, 25, Stats.NON_CRIT_DAMAGE.get(), ModType.MORE)
                )).registerToExileRegistry();


        // exceptional
        new SupportGem("rare_cooldown", "Greater Cooldown", PlayStyle.INT, 1.2F,
                Arrays.asList(new StatMod(15, 50, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT)
                )).edit(x -> {
            x.weight = 50;
        }).registerToExileRegistry();

        new SupportGem("rare_mana_eff", "Greater Mana Cost", PlayStyle.STR, 1,
                Arrays.asList(new StatMod(-15, -50, Stats.MANA_COST.get(), ModType.FLAT)
                )).edit(x -> {
            x.weight = 50;
        }).registerToExileRegistry();


    }
}
