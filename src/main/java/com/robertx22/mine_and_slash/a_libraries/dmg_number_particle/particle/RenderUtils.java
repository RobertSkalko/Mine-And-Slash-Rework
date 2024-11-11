package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import org.lwjgl.opengl.GL11;

public class RenderUtils {


    public static void renderText(PoseStack posestack, String text, float f, int color, MultiBufferSource.BufferSource multibuffersource$buffersource) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        Minecraft.getInstance().font.drawInBatch(text, f, 0.0F, color, false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.NORMAL, 0, 15728880);

        multibuffersource$buffersource.endBatch();

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }


    public static void renderComponent(PoseStack posestack, Component text, float f, int color, MultiBufferSource.BufferSource multibuffersource$buffersource) {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        Minecraft.getInstance().font.drawInBatch(text, f, 0.0F, color, false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.NORMAL, 0, 15728880);

        multibuffersource$buffersource.endBatch();

        RenderSystem.depthFunc(GL11.GL_LEQUAL);
    }
}
