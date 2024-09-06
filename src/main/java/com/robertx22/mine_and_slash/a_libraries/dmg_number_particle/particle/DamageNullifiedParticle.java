package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;

public class DamageNullifiedParticle extends ExileSpellResultParticle {
    private final Type type;

    public DamageNullifiedParticle(ClientLevel clientLevel, double x, double y, double z, Type type) {
        super(clientLevel, x, y, z);
        this.type = type;
    }

    @Override
    protected int getColor() {
        return ChatFormatting.WHITE.getColor();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupStyle(vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(vertexConsumer, camera, partialTick, posestack, () -> new Original.TextAndColor(type.text, getColor()));

        posestack.popPose();
    }

    public enum Type {
        DODGE("dodge"),
        RESIST("resist");

        public final String text;

        Type(String text) {
            this.text = text;
        }

    }
}
