package com.robertx22.age_of_exile.maps.processors.league;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LeagueControlBlockProcessor extends DataProcessor {

    public LeagueControlBlockProcessor() {
        super("league_control", Type.CONTAINS);
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


            world.setBlock(pos, SlashBlocks.LEAGUE.get().defaultBlockState(), 2);

            if (world.getBlockEntity(pos) instanceof LeagueBlockEntity lb) {
                lb.data.size = new LeagueBlockData.StructureRadius(x, z, y);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

