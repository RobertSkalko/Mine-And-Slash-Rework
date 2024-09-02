package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sun.jna.platform.win32.Winspool;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DamageNullifiedParticle extends DamageParticle2{
    @Override
    protected int getColor() {
        return ChatFormatting.WHITE.getColor();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        Vec3 cameraPos = camera.getPosition();
        double x = (float) (Mth.lerp((double) partialTick, this.xo, this.x));
        double y = (float) (Mth.lerp((double) partialTick, this.yo, this.y));
        double z = (float) (Mth.lerp((double) partialTick, this.zo, this.z));
        float scale = super.getScale(partialTick) * 0.035F;
        posestack.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);
        posestack.mulPose(camera.rotation());
        posestack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        posestack.scale(scale, scale, scale);
        posestack.translate(0.0F, -2.0F, 0.0F);

        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(type.text) / 2);
        Minecraft.getInstance().font.drawInBatch(type.text, f, 0.0F, getColor(), false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.NORMAL, 0, 15728880);
        multibuffersource$buffersource.endBatch();

        posestack.popPose();
    }

    public enum Type{
        DODGE("dodge"),
        RESIST("resist");

        public final String text;

        Type(String text) {
            this.text = text;
        }

    }

    private final Type type;

    public DamageNullifiedParticle(ClientLevel clientLevel, double x, double y, double z, Type type) {
        super(clientLevel, x, y, z);
        this.type = type;
    }
}
