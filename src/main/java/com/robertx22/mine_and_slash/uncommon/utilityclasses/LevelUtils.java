package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.levels.LevelInfo;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

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


    public static LevelRange tierToLevel(int tier) {
        return SkillItemTier.of(tier).levelRange;
    }

    public static int levelToTier(int level) {
        return SkillItemTier.fromLevel(level).tier;
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


    public static LevelInfo determineLevel(@Nullable LivingEntity en, Level world, BlockPos pos, Player nearestPlayer) {

        LevelInfo info = new LevelInfo();

        ServerLevel sw = (ServerLevel) world;

        if (WorldUtils.isMapWorldClass(world)) {
            try {
                var data = Load.mapAt(world, pos);
                if (data != null) {
                    info.set(LevelInfo.LevelSource.MAP_DIMENSION, data.map.getLevel());
                    return info;
                } else {
                    System.out.print("A mob spawned in a dungeon world without a dungeon data nearby!");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DimensionConfig dimConfig = ExileDB.getDimensionConfig(world);

        if (dimConfig.scale_to_nearest_player && nearestPlayer != null) {
            info.set(LevelInfo.LevelSource.NEAREST_PLAYER, Load.Unit(nearestPlayer).getLevel());
        } else {
            if (isInMinLevelArea(sw, pos, dimConfig)) {
                info.set(LevelInfo.LevelSource.MIN_LEVEL_AREA, dimConfig.min_lvl);
            } else {
                info.set(LevelInfo.LevelSource.DISTANCE_FROM_SPAWN, determineLevelPerDistanceFromSpawn(sw, pos, dimConfig));
            }
        }
        var varianceConfig = ServerContainer.get().MOB_LEVEL_VARIANCE.get();
        int variance = RandomUtils.RandomRange(-varianceConfig, varianceConfig);

        info.add(LevelInfo.LevelSource.LEVEL_VARIANCE, variance);
        info.capToRange(LevelInfo.LevelSource.DIMENSION, new MinMax(dimConfig.min_lvl, dimConfig.max_lvl));
        info.capToRange(LevelInfo.LevelSource.MAX_LEVEL, new MinMax(1, GameBalanceConfig.get().MAX_LEVEL));

        if (en != null) {
            var enconfig = ExileDB.getEntityConfig(en, Load.Unit(en));
            info.capToRange(LevelInfo.LevelSource.ENTITY_CONFIG, new MinMax(enconfig.min_lvl, enconfig.max_lvl));
        }

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
