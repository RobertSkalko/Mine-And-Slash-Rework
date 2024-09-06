package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileSpellResultParticle;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class Original implements IParticleRenderStrategy{

    ExileSpellResultParticle particle;

    @Override
    public void setupStyle(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack) {
        Vec3 cameraPos = camera.getPosition();
        Vec3 pos = particle.getPos();
        Vec3 original = particle.getOriginalPosition();
        double x = (float) (Mth.lerp((double) partialTick, original.x, pos.x));
        double y = (float) (Mth.lerp((double) partialTick, original.y, pos.y));
        double z = (float) (Mth.lerp((double) partialTick, original.z, pos.z));
        float scale = particle.getScale(partialTick) * 0.035F;
        posestack.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);
        posestack.mulPose(camera.rotation());
        posestack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        posestack.scale(scale, scale, scale);
        posestack.translate(0.0F, -2.0F, 0.0F);
    }

    @Override
    public void renderDamage(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, Supplier<?> extraInfo) {
        TextAndColor textAndColor = (TextAndColor)extraInfo.get();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(textAndColor.text) / 2);
        Minecraft.getInstance().font.drawInBatch(textAndColor.text, f, 0.0F, textAndColor.color, false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.NORMAL, 0, 15728880);
        multibuffersource$buffersource.endBatch();
    }


    @Override
    public void renderNullifiedDamage(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, Supplier<?> extraInfo) {
        TextAndColor textAndColor = (TextAndColor)extraInfo.get();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(textAndColor.text) / 2);
        Minecraft.getInstance().font.drawInBatch(textAndColor.text, f, 0.0F, textAndColor.color, false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.NORMAL, 0, 15728880);
        multibuffersource$buffersource.endBatch();
    }

    public record TextAndColor(String text, int color){}

}
