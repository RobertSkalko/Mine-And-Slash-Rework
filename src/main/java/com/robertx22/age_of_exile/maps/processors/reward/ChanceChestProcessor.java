package com.robertx22.age_of_exile.maps.processors.reward;

import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ChanceChestProcessor extends DataProcessor {

    public ChanceChestProcessor() {
        super("chance_chest", Type.CONTAINS);
        this.detectIds.add("chest_chance");
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {


        if (!data.chanceChest && RandomUtils.roll(25)) {
            data.chanceChest = true;
            new ChestProcessor().processImplementation(key, pos, world, data);
        } else {
            world.removeBlockEntity((pos)); // dont drop chest loot. this is a big problem if u remove this line
            world.removeBlock(pos, false);   // don't drop loot
            world.removeBlockEntity(pos);
        }

    }
}
