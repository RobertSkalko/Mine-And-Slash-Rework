package com.robertx22.mine_and_slash.uncommon.datasaving;

import com.robertx22.mine_and_slash.capability.chunk.ChunkData;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.capability.player.PlayerBackpackData;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.world.WorldData;
import com.robertx22.mine_and_slash.maps.MapData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class Load {

    // todo give a blank one for mobs


    public static EntityData Unit(Entity entity) {
        return entity.getCapability(EntityData.INSTANCE).orElse(new EntityData((LivingEntity) entity));
    }

    public static PlayerData player(Player player) {
        return player.getCapability(PlayerData.INSTANCE).orElse(new PlayerData(player));
    }

    public static PlayerBackpackData backpacks(Player player) {
        return player.getCapability(PlayerBackpackData.INSTANCE).orElse(null);
    }


    public static WorldData worldData(Level l) {
        return l.getServer().overworld().getCapability(WorldData.INSTANCE).orElse(null);
    }

    public static MapData mapAt(Level l, BlockPos pos) {
        try {
            MapData d = l.getServer().overworld().getCapability(WorldData.INSTANCE).orElse(null).map.getMap(pos).get();
            return d;
        } catch (Exception e) {
            // throw new RuntimeException(e);
        }
        return null;
    }

    public static ChunkData chunkData(LevelChunk c) {
        return c.getCapability(ChunkData.INSTANCE).orElseGet(null);
    }

}
