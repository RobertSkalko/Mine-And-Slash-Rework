package com.robertx22.age_of_exile.database.data.game_balance_config;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.world.entity.player.Player;

public class GameBalanceConfig implements JsonExileRegistry<GameBalanceConfig>, IAutoGson<GameBalanceConfig> {

    public static GameBalanceConfig SERIALIZER = new GameBalanceConfig();

    public String id = "game_balance";
    public static String ID = "game_balance";

    public static GameBalanceConfig get() {
        var d = Database.getRegistry(ExileRegistryTypes.GAME_BALANCE);
        if (d.isRegistered(ID)) {
            return (GameBalanceConfig) d.get(ID);
        }
        return SERIALIZER;
    }


    public int MAX_LEVEL = 100;

    public LevelScalingConfig NORMAL_STAT_SCALING = new LevelScalingConfig(1, 0.2F, false);
    public LevelScalingConfig SLOW_STAT_SCALING = new LevelScalingConfig(1, 0.01F, true);
    public LevelScalingConfig MANA_COST_SCALING = new LevelScalingConfig(1, 0.2F, true);
    public LevelScalingConfig CORE_STAT_SCALING = new LevelScalingConfig(1, 0.05F, true);
    public LevelScalingConfig STAT_REQ_SCALING = new LevelScalingConfig(2, 2, true);
    public LevelScalingConfig MOB_DAMAGE_SCALING = new LevelScalingConfig(1, 0.5F, false);

    public double TALENT_POINTS_PER_LVL = 1;
    public double STAT_POINTS_PER_LEVEL = 2;
    public int CLASS_POINTS_AT_MAX_LEVEL = 100;
    public int PASSIVE_POINTS_AT_MAX_LEVEL = 100;
    public double STARTING_TALENT_POINTS = 1;
    public double HP_DMG_MOB_BONUS_PER_MAP_TIER = 0.1;

    public int link_1_lvl = 1;
    public int link_2_lvl = 5;
    public int link_3_lvl = 10;
    public int link_4_lvl = 25;
    public int link_5_lvl = 50;

    public int getMaxLinksForLevel(int lvl) {
        if (lvl < link_1_lvl) {
            return 0;
        }
        if (lvl < link_2_lvl) {
            return 1;
        }
        if (lvl < link_3_lvl) {
            return 2;
        }
        if (lvl < link_4_lvl) {
            return 3;
        }
        if (lvl < link_5_lvl) {
            return 4;
        }
        return 5;
    }

    public int getTotalLinks(int links, Player p) {
        int max = Load.Unit(p).getLevel();
        return MathHelper.clamp(links, 0, max);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GAME_BALANCE;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<GameBalanceConfig> getClassForSerialization() {
        return GameBalanceConfig.class;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
