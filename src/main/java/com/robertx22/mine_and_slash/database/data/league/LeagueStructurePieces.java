package com.robertx22.mine_and_slash.database.data.league;

import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

public class LeagueStructurePieces implements IWeighted {

    public int size;
    public String folder;

    public LeagueStructurePieces(int size, String folder) {
        this.size = size;
        this.folder = folder;
    }

    public ResourceLocation getRoomForChunk(ChunkPos pos) {
        try {
            ChunkPos start = MapData.getStartChunk(pos.getMiddleBlockPosition(50));
            ChunkPos relative = new ChunkPos(pos.x - start.x, pos.z - start.z);
            int x = relative.x;
            int z = relative.z;

            if (isWithinBounds(x, z)) {
                return SlashRef.id("league/" + folder + "/" + x + "_" + z);
            }
        } catch (Exception e) {
        }
        return null;
    }

    boolean isWithinBounds(int x, int z) {
        int ax = Math.abs(x);
        int az = Math.abs(z);
        return ax <= size && az <= size;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
