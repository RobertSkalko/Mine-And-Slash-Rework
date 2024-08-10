package com.robertx22.mine_and_slash.maps.processors.league;

import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.mechanics.base.LeagueBlockData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LeagueControlBlockProcessor extends DataProcessor {

    public LeagueControlBlockProcessor() {
        super("league_control", Type.CONTAINS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {
        try {
            String[] parts = key.split(":");
            String numbers = parts[1];

            String[] nums = numbers.split("_");

            // radiuses and height
            int x = Integer.parseInt(nums[0]);
            int y = Integer.parseInt(nums[1]);
            int z = Integer.parseInt(nums[2]);


            world.setBlock(pos, SlashBlocks.LEAGUE_CONTROL.get().defaultBlockState(), 2);

            if (world.getBlockEntity(pos) instanceof LeagueControlBlockEntity lb) {
                lb.data.size = new LeagueBlockData.StructureRadius(x, z, y);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

