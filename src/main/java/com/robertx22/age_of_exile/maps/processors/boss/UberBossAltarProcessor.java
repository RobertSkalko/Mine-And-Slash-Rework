package com.robertx22.age_of_exile.maps.processors.boss;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

// todo create an arena with at least this altar
public class UberBossAltarProcessor extends DataProcessor {

    public UberBossAltarProcessor() {
        super("uber_boss_altar", DataProcessor.Type.EQUALS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {
        world.setBlock(pos, SlashBlocks.UBER_BOSS_ALTAR.get().defaultBlockState(), 2);
    }
}
