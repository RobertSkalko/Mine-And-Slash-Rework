package com.robertx22.age_of_exile.maps.generator;


import com.robertx22.age_of_exile.maps.DungeonRoom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ChunkProcessData {

    public ChunkProcessData(ChunkAccess chunk, BuiltRoom room) {
        this.chunk = chunk;
        this.room = room;
    }

    public ChunkAccess chunk;
    private BuiltRoom room;
    public boolean chanceChest = false;

    public DungeonRoom getRoom() {
        return room.room;
    }

    public void iterateBlocks(Function<BlockPos, BlockPos> function) {

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 100; y++) {
                for (int z = 0; z < 16; z++) {
                    function.apply(chunk.getPos().getBlockAt(0, 0, 0).offset(x, y, z));
                }
            }
        }

    }

    public List<BlockPos> getBlockPosList() {

        List<BlockPos> list = new ArrayList<>();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 100; y++) {
                for (int z = 0; z < 16; z++) {
                    list.add(chunk.getPos()
                            .getBlockAt(0, 0, 0)
                            .offset(x, y, z));
                }
            }
        }

        return list;
    }

}
