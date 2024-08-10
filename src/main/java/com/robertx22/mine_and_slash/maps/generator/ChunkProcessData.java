package com.robertx22.mine_and_slash.maps.generator;


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

    public BuiltRoom getRoom() {
        return room;
    }

    public void iterateBlocks(Function<BlockPos, BlockPos> function) {

        for (int x = 0; x < 16; x++) {
            for (int y = -16; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    function.apply(chunk.getPos().getBlockAt(0, 0, 0).offset(x, y, z));
                }
            }
        }

    }

    // todo get rid of this maybe, or improve it
    public List<BlockPos> getBlockPosList() {


        List<BlockPos> list = new ArrayList<>();

        var bpos = chunk.getPos().getBlockAt(0, 0, 0);
        for (int x = 0; x < 16; x++) {
            for (int y = -16; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    list.add(bpos.offset(x, y, z));
                }
            }
        }

        return list;
    }

}
