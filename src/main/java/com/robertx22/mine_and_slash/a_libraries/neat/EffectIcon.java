package com.robertx22.mine_and_slash.a_libraries.neat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

import java.util.function.Function;

public record EffectIcon(ResourceLocation location, int stack) {

    public static EffectIcon of(ResourceLocation location, int stack){
        return new EffectIcon(location, stack);
    }

    public void renderOnHealthBar(PoseStack poseStack, MultiBufferSource source, int size){
        VertexConsumer buffer = source.getBuffer(NeatRenderType.getHealthBarIconType(location));
        Matrix4f pose = new Matrix4f(poseStack.last().pose());
        int alpha = 200;
        int i = 255;
        Font font = Minecraft.getInstance().font;
        float fontScale = 0.5f * size / font.lineHeight;
        buffer.vertex(pose, 0, 0, 0.01f).color(i, i , i, alpha).uv(0, 0).endVertex();
        buffer.vertex(pose, 0, size, 0.01f).color(i, i , i, alpha).uv(0, 1).endVertex();
        buffer.vertex(pose, size, size, 0.01f).color(i, i , i, alpha).uv(1, 1).endVertex();
        buffer.vertex(pose, size, 0, 0.01f).color(i, i , i, alpha).uv(1, 0).endVertex();
        poseStack.pushPose();

        //poseStack.translate(size - font.width(stack + ""), size - font.lineHeight, 0);
        poseStack.scale(fontScale, fontScale,1);
        font.drawInBatch(stack + "", 0, 0, ChatFormatting.WHITE.getColor(), false, new Matrix4f(poseStack.last().pose()), source, Font.DisplayMode.NORMAL, 0, 15728880);
        poseStack.popPose();

    }
}
