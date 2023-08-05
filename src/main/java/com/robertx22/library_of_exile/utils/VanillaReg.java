package com.robertx22.library_of_exile.utils;

import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;



public class VanillaReg {

    public static ResourceLocation getId(Level world) {

        return world.dimension()
            .location();


    }

    public static ResourceLocation getId(Item item) {
        return VanillaUTIL.REGISTRY.items().getKey(item);
    }

}
