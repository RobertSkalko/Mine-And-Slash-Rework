package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.util.concurrent.RateLimiter;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import net.minecraft.resources.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PainterController {

    public final static String nameSpace = "etexture";
    public static RateLimiter paintLimiter = RateLimiter.create(400.0D, 2, TimeUnit.SECONDS);
    public static RateLimiter registerLimiter = RateLimiter.create(ClientConfigs.getConfig().PAINTED_TEXTURE_REGISTER_SPEED.get(), 10, TimeUnit.SECONDS);



    public record BufferedImagePack(BufferedImage image, ResourceLocation resourceLocation) {
        @Override
        public BufferedImage image() {
            return image;
        }

        @Override
        public ResourceLocation resourceLocation() {
            return resourceLocation;
        }
    }

}
