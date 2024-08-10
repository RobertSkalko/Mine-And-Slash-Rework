package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

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


        if (true) {
            //   renderNameTag(gui, camera, particle.renderString, particle, gui.pose(), 10000);
            return;
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

        // matrix.translate(x, y, z);
        matrix.translate(x - camX, y - camY, z - camZ);

        matrix.mulPose(client.getEntityRenderDispatcher().cameraOrientation());
        matrix.mulPose(Axis.YP.rotationDegrees(180.0F));

        matrix.scale(scaleToGui, scaleToGui, scaleToGui);


        //   matrix.mulPose(camera.rotation());
        //matrix.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));


        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        drawDamageNumber(gui, particle.renderString, 0, 0, 10);

        matrix.popPose();
    }

    public static void renderNameTag(Camera camera, String name, DamageParticle particle, PoseStack matrix, float tickDelta, MultiBufferSource pBuffer) {
        Minecraft mc = Minecraft.getInstance();


        double x = Mth.lerp((double) tickDelta, particle.xPrev, particle.x);
        double y = Mth.lerp((double) tickDelta, particle.yPrev, particle.y);
        double z = Mth.lerp((double) tickDelta, particle.zPrev, particle.z);


        Vec3 camPos = camera.getPosition();
        double camX = camPos.x;
        double camY = camPos.y;
        double camZ = camPos.z;


        int i = "deadmau5".equals(name) ? -10 : 0;
        matrix.pushPose();

        matrix.translate(x, y, z);
        //   matrix.translate(x - camX, y - camY, z - camZ);

        //matrix.translate(0.0F, y, 0.0F);

        matrix.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
        matrix.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrix.last().pose();
        float f1 = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
        int j = (int) (f1 * 255.0F) << 24;
        Font font = Minecraft.getInstance().font;
        float f2 = (float) (-font.width(name) / 2);

        // gui.drawString(mc.font, name, 0, 0, ChatFormatting.RED.getColor());


        font.drawInBatch(name, f2, (float) i, 553648127, false, matrix4f, pBuffer, Font.DisplayMode.NORMAL, j, 10000);


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