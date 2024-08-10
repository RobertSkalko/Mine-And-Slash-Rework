package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.TreeOptimizationHandler;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter.AllPerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter.PerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionCache;
import net.minecraft.resources.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PainterController {

    public final static String nameSpace = "etexture";
    public static RateLimiter paintLimiter = RateLimiter.create(400.0D, 2, TimeUnit.SECONDS);
    public static RateLimiter registerLimiter = RateLimiter.create(ClientConfigs.getConfig().PAINTED_TEXTURE_REGISTER_SPEED.get(), 10, TimeUnit.SECONDS);
    public static boolean isRegistering = false;
    public static ConcurrentHashMap<Integer, AllPerkButtonPainter> container = new ConcurrentHashMap<>();


    public static void resetAllPaintingWhenDrinkResetPotion() {
        if (TreeOptimizationHandler.isOptEnable()) {
            TreeOptimizationHandler.runOptTask(() -> {
                for (TalentTree.SchoolType value : TalentTree.SchoolType.values()) {
                    AllPerkButtonPainter.getPainter(value).startANewRun();
                    PerkConnectionCache.reset();
                }

            });

        }
    }

    public static void performEachTickPainterJob() {
        if (TreeOptimizationHandler.isOptEnable()){
            container.values().forEach(x -> {
                x.checkIfNeedRepaint();
                x.scheduleRepaint();
            });



        RenderSystem.recordRenderCall(() -> {
            if (!isRegistering){
                PerkButtonPainter.handleRegisterQueue();
            }
            container.values().forEach(AllPerkButtonPainter::tryRegister);
        });
        }
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
