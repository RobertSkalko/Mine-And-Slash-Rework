package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.capability.ChunkPopulatedCap;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;

public class Load {

    public static EntitySpellCap.ISpellsCap spells(LivingEntity provider) {
        return provider.getCapability(EntitySpellCap.Data)
                .orElse(null);
    }

    public static ChunkPopulatedCap chunkPopulated(LevelChunk chunk) {
        return chunk.getCapability(ChunkPopulatedCap.Data)
                .orElse(null);
    }

    public static EntityData Unit(Entity entity) {
        return entity.getCapability(EntityData.Data)
                .orElse(new EntityData((LivingEntity) entity));
    }

    public static RPGPlayerData playerRPGData(Player player) {
        return player.getCapability(RPGPlayerData.Data)
                .orElse(null);
    }

  
}
