package com.robertx22.age_of_exile.maps.generator.processors;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;


public class BossAltSpellingProcessor extends DataProcessor {

    public BossAltSpellingProcessor() {
        super("boss");
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        new BossProcessor().processImplementation(key, pos, world, data);

    }
}
