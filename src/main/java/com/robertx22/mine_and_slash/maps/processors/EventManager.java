package com.robertx22.mine_and_slash.maps.processors;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.StringUTIL;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// todo use this on boss changes
public class EventManager {

    public static void callEvent(BlockPos pos, Level level, String eventCalled, int radius) {

        ChunkPos start = new ChunkPos(pos);

        List<ChunkPos> chunks = new ArrayList<>();
        chunks.add(new ChunkPos(pos));

        for (int i = 1; i < radius; i++) {
            chunks.add(new ChunkPos(start.x + i, start.z));
            chunks.add(new ChunkPos(start.x - i, start.z));
            chunks.add(new ChunkPos(start.x, start.z + i));
            chunks.add(new ChunkPos(start.x, start.z - i));

            chunks.add(new ChunkPos(start.x + i, start.z + i));
            chunks.add(new ChunkPos(start.x - i, start.z - i));
            chunks.add(new ChunkPos(start.x - i, start.z + i));
            chunks.add(new ChunkPos(start.x + i, start.z - i));
        }

        for (ChunkPos cpos : chunks) {
            if (!level.hasChunk(cpos.x, cpos.z)) {
                continue;
            }
            ChunkAccess c = level.getChunk(cpos.x, cpos.z);

            if (c instanceof LevelChunk chunk) {
                for (Map.Entry<BlockPos, BlockEntity> en : chunk.getBlockEntities().entrySet()) {
                    if (en.getValue() instanceof CommandBlockEntity cb) {
                        callOnBlock(DataProcessor.getData(cb), en.getKey(), level, eventCalled);
                    }
                }
            }
        }

    }

    private static void callOnBlock(String key, BlockPos pos, Level world, String eventCalled) {
        if (key.contains("event_listener")) {

            String[] parts = StringUTIL.split(key, ";");
            String event = parts[1];

            if (event.equals(eventCalled)) {
                String type = parts[2];

                if (type.equals("redstone_block")) {
                    world.setBlock(pos.above(), Blocks.REDSTONE_BLOCK.defaultBlockState(), 2);
                }

            }
        }
    }
}
