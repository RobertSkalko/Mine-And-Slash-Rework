package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.capability.chunk.ChunkData;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellData;
import com.robertx22.age_of_exile.capability.player.PlayerBackpackData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.world.WorldData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class Load {

    // todo give a blank one for mobs

    public static EntitySpellData.ISpellsCap spells(LivingEntity provider) {
        return provider.getCapability(EntitySpellData.INSTANCE)
                .orElse(null);
    }


    public static EntityData Unit(Entity entity) {
        return entity.getCapability(EntityData.INSTANCE)
                .orElse(new EntityData((LivingEntity) entity));
    }

    public static PlayerData playerRPGData(Player player) {
        return player.getCapability(PlayerData.INSTANCE)
                .orElse(null);
    }

    public static PlayerBackpackData backpacks(Player player) {
        return player.getCapability(PlayerBackpackData.INSTANCE).orElse(null);
    }


    public static WorldData worldData(Level l) {
        return l.getServer().overworld().getCapability(WorldData.INSTANCE).orElse(null);
    }

    public static ChunkData chunkData(LevelChunk c) {
        return c.getCapability(ChunkData.INSTANCE).orElseGet(null);
    }

}
