package com.robertx22.mine_and_slash.maps.processors.league;

import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class MapTeleporterProcessor extends DataProcessor {

    public MapTeleporterProcessor() {
        super("map_teleporter", Type.EQUALS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {
        world.setBlock(pos, SlashBlocks.MAP.get().defaultBlockState(), 2);
    }
}
