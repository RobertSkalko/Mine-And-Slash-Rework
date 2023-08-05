package com.robertx22.age_of_exile.a_libraries.dmg_number_particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;


public class DamageParticleRenderer {

    public static Set<DamageParticle> PARTICLES = new HashSet<>();

    public static void renderParticles(PoseStack matrix, Camera camera) {
        for (DamageParticle p : PARTICLES) {
            renderParticle(matrix, p, camera);
            p.tick();
        }

        PARTICLES.removeIf(x -> x.age > 50);
    }

    private static void renderParticle(PoseStack matrix, DamageParticle particle, Camera camera) {
        float scaleToGui = 0.025f;

        if (particle.packet.iscrit) {
            scaleToGui *= 2;
        }

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

        RenderSystem.disableLighting();
        RenderSystem.enableDepthTest();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE,
                GL11.GL_ZERO);
        RenderSystem.shadeModel(7425);

        drawDamageNumber(matrix, particle.renderString, 0, 0, 10);

        RenderSystem.shadeModel(7424);
        RenderSystem.disableBlend();
        RenderSystem.enableAlphaTest();

        matrix.popPose();
    }

    public static void drawDamageNumber(PoseStack matrix, String s, double x, double y,
                                        float width) {

        Minecraft minecraft = Minecraft.getInstance();
        int sw = minecraft.font.width(s);
        minecraft.font.drawShadow(matrix, s, (int) (x + (width / 2) - sw), (int) y + 5, ChatFormatting.RED.getColor());
    }
}