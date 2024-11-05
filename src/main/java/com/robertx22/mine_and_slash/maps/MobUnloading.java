package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

public class MobUnloading {

    public static void onUnloadMob(LivingEntity en) {
        try {
            if (WorldUtils.isMapWorldClass(en.level())) {
                var cp = new ChunkPos(en.blockPosition());
                if (en.level().hasChunk(cp.x, cp.z)) {
                    var chunk = en.level().getChunkAt(en.blockPosition());
                    Load.chunkData(chunk).trySaveMob(en);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBackMobs(Level level, ChunkPos cp) {
        try {
            if (WorldUtils.isMapWorldClass(level)) {
                // we dont want to force load chunks
                if (level.hasChunk(cp.x, cp.z)) {
                    var chunk = level.getChunk(cp.x, cp.z);
                    Load.chunkData(chunk).tryLoadMobs(level);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
