package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ClientTextureUtils {

    public static boolean textureExists(ResourceLocation id) {
        return Minecraft.getInstance()
                .getResourceManager().getResource(id).isPresent();
        //       .hasResource(id);
    }

}