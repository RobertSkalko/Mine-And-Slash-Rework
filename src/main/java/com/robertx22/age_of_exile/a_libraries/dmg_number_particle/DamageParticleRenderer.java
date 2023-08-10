package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;


public class DamageParticleRenderer {

    // todo might need to do without dmg particles for a while.

    public static Set<DamageParticle> PARTICLES = new HashSet<>();

    public static void renderParticles(GuiGraphics gui, Camera camera) {
        for (DamageParticle p : PARTICLES) {
            renderParticle(gui, p, camera);
            p.tick();
        }

        PARTICLES.removeIf(x -> x.age > 50);
    }

    private static void renderParticle(GuiGraphics gui, DamageParticle particle, Camera camera) {
        float scaleToGui = 0.025f;

        if (particle.packet.iscrit) {
            scaleToGui *= 2;
        }

        var matrix = gui.pose();

        Minecraft client = Minecraft.getInstance();
        float tickDelta = client.getFrameTime();

        double x = Mth.lerp((double) tickDelta, particle.xPrev, particle.x);
        double y = Mth.lerp((double) tickDelta, particle.yPrev, particle.y);
        double z = Mth.lerp((double) tickDelta, particle.zPrev, particle.z);

        Vec3 camPos = camera.getPosition();
        double camX = camPos.x;
        double camY = camPos.y;
        double camZ = camPos.z;

        matrix.pushPose();

        matrix.translate(x - camX, y - camY, z - camZ);

        matrix.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
        matrix.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));
        matrix.scale(-scaleToGui, -scaleToGui, scaleToGui);

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        drawDamageNumber(gui, particle.renderString, 0, 0, 10);

        matrix.popPose();
    }

    public static void drawDamageNumber(GuiGraphics gui, String s, double x, double y,
                                        float width) {

        Minecraft minecraft = Minecraft.getInstance();
        int sw = minecraft.font.width(s);


        //gui.drawString(minecraft.font, s, (int) (x + (width / 2) - sw), (int) y + 5, ChatFormatting.RED.getColor());
        gui.drawString(minecraft.font, s, 0, 0, ChatFormatting.RED.getColor());
    }


    /*
    public static void drawDamageNumber(GuiGraphics gui,PoseStack matrix, String s, double x, double y,
                                        float width) {

           Minecraft minecraft = Minecraft.getInstance();
        int sw = minecraft.tex.getWidth(s);
         minecraft.font.drawInBatch(matrix, s, (int) (x + (width / 2) - sw), (int) y + 5, ChatFormatting.WHITE.getColor());
    }



     */

}