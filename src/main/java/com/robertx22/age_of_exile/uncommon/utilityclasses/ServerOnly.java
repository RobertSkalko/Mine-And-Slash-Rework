package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.UUID;

public class ServerOnly {

    public static Entity getEntityByUUID(Level world, UUID id) {

        if (world instanceof ServerLevel) {
            return ((ServerLevel) world).getEntity(id);
        }

        return null;

    }

}
