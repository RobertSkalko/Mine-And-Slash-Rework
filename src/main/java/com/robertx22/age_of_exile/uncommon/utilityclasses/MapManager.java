package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MapManager {

    public static ResourceLocation getResourceLocation(Level world) {
        ResourceLocation loc = world.dimension()
            .location();
        return loc;
    }
}
