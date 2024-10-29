package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class ElementDamageParticle extends ExileInteractionResultParticle {
    private final int color;
    private final String damageString;

    public ElementDamageParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, int color, String damageString) {
        super(clientLevel, x, y, z, strategy);
        this.color = color;
        this.damageString = damageString;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();
        super.getStrategy().setupParticle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(this, vertexConsumer, camera, partialTick, posestack, damageString, getColor());
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    @Override
    public void tick() {
        super.tick();
        super.getStrategy().tick(this);
    }


}
