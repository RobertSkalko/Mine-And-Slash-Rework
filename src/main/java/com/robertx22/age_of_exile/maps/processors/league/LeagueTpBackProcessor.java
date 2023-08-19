package com.robertx22.age_of_exile.maps.processors.league;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class LeagueTpBackProcessor extends DataProcessor {

    public LeagueTpBackProcessor() {
        super("league_back", Type.EQUALS);
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {


        world.setBlock(pos, SlashBlocks.HARVEST_TELEPORT.get().defaultBlockState(), 2); // todo make a generic tp back one


    }
}
