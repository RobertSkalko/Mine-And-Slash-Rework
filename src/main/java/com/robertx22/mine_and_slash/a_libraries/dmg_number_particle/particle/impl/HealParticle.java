package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderMaterial;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class HealParticle extends ExileInteractionResultParticle {
    private final float amount;
    public static final int color = ChatFormatting.GREEN.getColor();

    public HealParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, float amount) {
        super(clientLevel, x, y, z, strategy);
        this.amount = amount;
    }


    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupParticle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderHeal(this, vertexConsumer, camera, partialTick, posestack, new IParticleRenderMaterial.simpleText(NumberUtils.format(amount)));
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    @Override
    public void tick() {
        super.tick();
        super.getStrategy().tick(this);
    }

}
