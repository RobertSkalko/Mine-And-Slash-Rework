package com.robertx22.mine_and_slash.aoe_data;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.Prefixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.Suffixes;
import com.robertx22.mine_and_slash.aoe_data.database.auto_items.AutoItems;
import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearsAdder;
import com.robertx22.mine_and_slash.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.mine_and_slash.aoe_data.database.chaos_stat.ChaosStats;
import com.robertx22.mine_and_slash.aoe_data.database.custom_item_gens.CustomItems;
import com.robertx22.mine_and_slash.aoe_data.database.dim_configs.DimConfigs;
import com.robertx22.mine_and_slash.aoe_data.database.entity_configs.EntityConfigs;
import com.robertx22.mine_and_slash.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.mine_and_slash.aoe_data.database.gear_rarities.GearRaritiesAdder;
import com.robertx22.mine_and_slash.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.mine_and_slash.aoe_data.database.gems.Gems;
import com.robertx22.mine_and_slash.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.mine_and_slash.aoe_data.database.mob_rarities.MobRarities;
import com.robertx22.mine_and_slash.aoe_data.database.perks.AllPerks;
import com.robertx22.mine_and_slash.aoe_data.database.prophecies.ProphecyModifiers;
import com.robertx22.mine_and_slash.aoe_data.database.runes.Runes;
import com.robertx22.mine_and_slash.aoe_data.database.runewords.Runewords;
import com.robertx22.mine_and_slash.aoe_data.database.spell_schools.SpellSchoolsAdder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.aoe_data.database.spells.Spells;
import com.robertx22.mine_and_slash.aoe_data.database.stat_compats.StatCompats;
import com.robertx22.mine_and_slash.aoe_data.database.stat_conditions.StatConditions;
import com.robertx22.mine_and_slash.aoe_data.database.stat_effects.StatEffects;
import com.robertx22.mine_and_slash.aoe_data.database.stats.Stats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.AutoDatapackStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.UniqueGearReg;
import com.robertx22.mine_and_slash.content.ubers.UberBosses;
import com.robertx22.mine_and_slash.database.data.aura.AuraGems;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsConfig;
import com.robertx22.mine_and_slash.database.data.game_balance_config.PlayerPointsType;
import com.robertx22.mine_and_slash.database.data.map_affix.MapAffixes;
import com.robertx22.mine_and_slash.database.data.omen.Omens;
import com.robertx22.mine_and_slash.database.data.profession.all.ProfessionRecipes;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.buffs.StatBuffs;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.support_gem.SupportGems;
import com.robertx22.mine_and_slash.maps.dungeon_reg.Dungeons;
import com.robertx22.mine_and_slash.maps.spawned_map_mobs.SpawnedMobs;
import com.robertx22.mine_and_slash.uncommon.enumclasses.WeaponTypes;

public class GeneratedData {


    // as these only add serizables.
    // They shouldn't be needed at all to play the game.
    // If it errors without them, then that means i hardcoded something i shouldn't have
    public static void addAllObjectsToGenerate() {

   
        AuraGems.initKeys();

        WeaponTypes.registerAll();
        StatLayers.register();
        new StatEffects().registerAll();
        new StatConditions().registerAll();
        new Stats().registerAll();
        new DatapackStats().registerAll();

        new GearRaritiesAdder().registerAll();

        SpellCalcs.init();
        new Spells().registerAll();
        new SpellSchoolsAdder().registerAll();


        new GearSlots().registerAll();
        new BaseGearsAdder().registerAll();
        new UniqueGearReg().registerAll();

        new ModEffects().registerAll();

        new Prefixes().registerAll();
        new Suffixes().registerAll();

        new MobAffixes().registerAll();
        new MobRarities().registerAll();
        new DimConfigs().registerAll();
        new EntityConfigs().registerAll();

        new ChaosStats().registerAll();
        new Gems().registerAll();
        new Runes().registerAll();

        new AllPerks().registerAll();

        new AutoItems().registerAll();
        new CustomItems().registerAll();

        new AutoDatapackStats().registerAll();

        new BaseStatsAdder().registerAll();

        new Runewords().registerAll();

        SpawnedMobs.init();
        Dungeons.init();

        Professions.init();
        ProfessionRecipes.init();
        StatBuffs.init();

        MapAffixes.init();
        SupportGems.init();
        AuraGems.init();

        new StatCompats().registerAll();

        new ProphecyModifiers().registerAll();
        UberBosses.init();

        GameBalanceConfig c = new GameBalanceConfig();
        c.player_points.put(PlayerPointsType.TALENTS, new PlayerPointsConfig(PlayerPointsType.TALENTS, 1, 1, 30, 200));
        c.player_points.put(PlayerPointsType.ASCENDANCY, new PlayerPointsConfig(PlayerPointsType.ASCENDANCY, 0, 0.1F, 0, 9));
        c.player_points.put(PlayerPointsType.SPELLS, new PlayerPointsConfig(PlayerPointsType.SPELLS, 0, 1, 10, 150));
        c.player_points.put(PlayerPointsType.PASSIVES, new PlayerPointsConfig(PlayerPointsType.PASSIVES, 0, 1, 10, 150));
        c.player_points.put(PlayerPointsType.STATS, new PlayerPointsConfig(PlayerPointsType.STATS, 0, 2, 50, 300));
        c.addToSerializables();

        Omens.init();

    }
}
