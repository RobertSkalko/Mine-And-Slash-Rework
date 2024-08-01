package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.util.concurrent.RateLimiter;
import net.minecraft.resources.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PainterController {

    public final static String nameSpace = "etexture";
    private final static HashMap<Class, Boolean> updatePermission = new HashMap<>();
    public static RateLimiter paintLimiter = RateLimiter.create(400.0D, 2, TimeUnit.SECONDS);
    public static RateLimiter registerLimiter = RateLimiter.create(2000D, 10, TimeUnit.SECONDS);

    public static RateLimiter bigTextureRegisterLimiter = RateLimiter.create(5);

    public static void setAllowUpdate() {
        updatePermission.forEach((k, v) -> {
            updatePermission.put(k, true);
        });
    }

    public static void setForbidUpdate() {
        updatePermission.forEach((k, v) -> {
            updatePermission.put(k, false);
        });
    }

    public static <T> void passOnePaintAction(T target){
        updatePermission.put(target.getClass(), false);
    }

    public static <T> Boolean isAllowedUpdate(T target){
        Boolean b = updatePermission.putIfAbsent(target.getClass(), false);

        return b != null && b;
    }

    public static <T> void setThisAllowedUpdate(T target){
        updatePermission.put(target.getClass(), true);

    }

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
