package com.robertx22.mine_and_slash.maps.processors.reward;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.generator.ChunkProcessData;
import com.robertx22.mine_and_slash.maps.processors.DataProcessor;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class MapRewardChestProcessor extends DataProcessor {

    public MapRewardChestProcessor() {
        super("map_reward", Type.EQUALS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {
        try {
            var map = Load.mapAt(world, pos);

            if (map == null) {
                return;
            }
            var rar = ExileDB.GearRarities().get(map.completion_rarity);

            if (data.map_reward_chests++ < rar.map_reward.reward_chests) {
                ChestProcessor.createChest(world, pos, false, rar.map_reward.loot_table);
            } else {
                world.removeBlockEntity((pos)); // dont drop chest loot. this is a big problem if u remove this line
                world.removeBlock(pos, false);   // don't drop loot
                world.removeBlockEntity(pos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

