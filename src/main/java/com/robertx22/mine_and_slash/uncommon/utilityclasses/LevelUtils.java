package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LevelUtils {


    public static int getMaxTier() {
        return levelToTier(GameBalanceConfig.get().MAX_LEVEL);
    }


    public static void runTests() {
        if (MMORPG.RUN_DEV_TOOLS) {

            // Preconditions.checkArgument(levelToTier(15) == 0);
            //Preconditions.checkArgument(levelToTier(25) == 1);

        }
    }

    public static String tierToRomanNumeral(int tier) {
        return RomanNumber.toRoman(tier);
    }

    public static LevelRange tierToLevel(int tier) {
        return SkillItemTier.of(tier).levelRange;
    }

    public static int levelToTier(int level) {
        return SkillItemTier.fromLevel(level).tier;
    }

    public static SkillItemTier levelToSkillTier(int lvl) {
        return SkillItemTier.fromLevel(lvl);
    }

    public static int getDistanceFromMaxLevel(int lvl) {
        int max = GameBalanceConfig.get().MAX_LEVEL;
        return Math.abs(lvl - max);
    }

    public static float getMaxLevelMultiplier(float lvl) {
        float max = GameBalanceConfig.get().MAX_LEVEL;
        return (float) lvl / max;
    }

    public static int getExpRequiredForLevel(int level) {
        return (int) (Math.pow(10F * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(level), 2.4F));
    }


    public static int getBaseExpMobReward(int level) {
        return 50 + scaleExpReward(4, level);
    }

    public static int scaleExpReward(int exp, int level) {
        return (int) (Math.pow(exp * GameBalanceConfig.get().NORMAL_STAT_SCALING.getMultiFor(level), 1.1F));
    }

    public static class LevelDetermInfo {

        public int level;

    }

    public static LevelDetermInfo determineLevel(Level world, BlockPos pos, Player nearestPlayer) {
        LevelDetermInfo info = new LevelDetermInfo();

        if (nearestPlayer != null && MMORPG.RUN_DEV_TOOLS) {
            info.level = Load.Unit(nearestPlayer).getLevel();
            return info;
        }

        ServerLevel sw = (ServerLevel) world;

        DimensionConfig dimConfig = ExileDB.getDimensionConfig(world);

        int lvl = 0;

        if (ServerContainer.get().ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER.get() || dimConfig.scale_to_nearest_player) {
            if (nearestPlayer != null) {

                if (isInMinLevelArea(sw, pos, dimConfig)) {
                    lvl = dimConfig.min_lvl;
                } else {
                    lvl = Load.Unit(nearestPlayer)
                            .getLevel();
                }
            } else {
                lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
            }
        } else {
            lvl = determineLevelPerDistanceFromSpawn(sw, pos, dimConfig);
        }

        var LevelVariance = ServerContainer.get().MOB_LEVEL_VARIANCE.get();

        lvl = RandomUtils.RandomRange(lvl - LevelVariance, lvl + LevelVariance);

        lvl = Mth.clamp(lvl, dimConfig.min_lvl, dimConfig.max_lvl);
        lvl = Mth.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);


        lvl = Mth.clamp(lvl, dimConfig.min_lvl, dimConfig.max_lvl);
        lvl = Mth.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

        info.level = lvl;

        return info;
    }

    public static boolean isInMinLevelArea(ServerLevel world, BlockPos pos, DimensionConfig config) {
        double distance = world.getSharedSpawnPos()
                .distManhattan(pos);

        double scale = Mth.clamp(world.dimensionType()
                .coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        distance *= scale;

        if (distance < config.min_lvl_area) {
            return true;
        }
        return false;
    }

    public static int determineLevelPerDistanceFromSpawn(ServerLevel world, BlockPos pos, DimensionConfig config) {

        double distance = world.getSharedSpawnPos().distManhattan(pos);

        double scale = Mth.clamp(world.dimensionType().coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        distance *= scale;

        if (distance < config.min_lvl_area) {
            return config.min_lvl;
        }

        int lvl = 1;

        lvl = (int) (config.min_lvl + ((distance - config.min_lvl_area) / (config.mob_lvl_per_distance)));

        return Mth.clamp(lvl, config.min_lvl, config.max_lvl);

    }

    public static double getBlocksForEachLevelDistance(ServerLevel world) {
        DimensionConfig config = ExileDB.getDimensionConfig(world);

        double scale = Mth.clamp(world.dimensionType()
                .coordinateScale() / 3F, 1, Integer.MAX_VALUE);

        return config.mob_lvl_per_distance / scale;
    }

}
