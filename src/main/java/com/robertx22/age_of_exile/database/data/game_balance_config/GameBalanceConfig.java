package com.robertx22.age_of_exile.database.data.game_balance_config;

import com.robertx22.age_of_exile.database.data.game_balance_config.lvltiers.LevelTier;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;

public class GameBalanceConfig implements JsonExileRegistry<GameBalanceConfig>, IAutoGson<GameBalanceConfig> {

    public static GameBalanceConfig SERIALIZER = new GameBalanceConfig();

    public String id = "game_balance";
    public static String ID = "game_balance";

    public static GameBalanceConfig get() {
        return (GameBalanceConfig) Database.getRegistry(ExileRegistryTypes.GAME_BALANCE).get(ID);
    }


    public int MAX_LEVEL = 100;
    public int levels_per_tier = 20;

    public LevelScalingConfig NORMAL_STAT_SCALING = new LevelScalingConfig(1, 0.2F, false);
    public LevelScalingConfig SLOW_STAT_SCALING = new LevelScalingConfig(1, 0.01F, true);
    public LevelScalingConfig MANA_COST_SCALING = new LevelScalingConfig(1, 0.2F, true);
    public LevelScalingConfig CORE_STAT_SCALING = new LevelScalingConfig(1, 0.05F, true);
    public LevelScalingConfig STAT_REQ_SCALING = new LevelScalingConfig(2, 2, true);
    public LevelScalingConfig MOB_DAMAGE_SCALING = new LevelScalingConfig(1, 0.5F, false);

    public double TALENT_POINTS_PER_LVL = 1;
    public double STAT_POINTS_PER_LEVEL = 2;
    public int CLASS_POINTS_AT_MAX_LEVEL = 4;
    public double STARTING_TALENT_POINTS = 1;


    // this is kinda cursed but will probably work
    public int getTier(int lvl) {
        if (lvl == MAX_LEVEL) {
            return (Math.clamp(lvl - 1, 0, 5)) / levels_per_tier; // we dont want to add a new tier just for max lvl
        }
        return lvl / levels_per_tier;

    }

    public LevelTier getLevelsOfTier(int tier) {
        try {
            return getTiers().stream().filter(x -> x.tier == tier).findFirst().get();
        } catch (Exception e) {
            if (tier < 2) {
                return getTiers().get(0);
            } else {
                return getTiers().get(getTiers().size() - 1);
            }
        }


/*
        float multi = tier / 5F;
        int testlvl = (int) (MAX_LEVEL * multi + 1);

        testlvl = Math.clamp(testlvl, 1, MAX_LEVEL);

        int finalTestlvl = testlvl;
        var opt = LevelRanges.allNormal().stream().filter(x -> x.isLevelInRange(finalTestlvl)).findFirst();

        if (!opt.isPresent()) {
            System.out.println("tier " + tier + " cant be found");
        }
        return opt.get();

 */

    }


    public List<LevelTier> getTiers() {
        List<LevelTier> list = new ArrayList<>();

        int min = 0;
        int max = levels_per_tier;
        for (int i = 0; i < 6; i++) {
            var fmin = Math.clamp(min + 1, 1, MAX_LEVEL);
            var fmax = Math.clamp(max, 0, MAX_LEVEL);

            if (fmin > MAX_LEVEL) {
                fmin = MAX_LEVEL;
            }
            if (fmax > MAX_LEVEL) {
                fmax = MAX_LEVEL;
            }
            list.add(new LevelTier(i, fmin, fmax));
            min += levels_per_tier;
            max += levels_per_tier;
        }

        return list;

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
