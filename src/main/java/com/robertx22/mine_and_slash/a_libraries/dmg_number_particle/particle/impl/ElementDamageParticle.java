package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderMaterial;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class ElementDamageParticle extends ExileInteractionResultParticle {
    private final IParticleRenderMaterial mat;

    public ElementDamageParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, IParticleRenderMaterial mat) {
        super(clientLevel, x, y, z, strategy);
        this.mat = mat;
    }



    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();
        super.getStrategy().setupParticle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(this, vertexConsumer, camera, partialTick, posestack, mat);
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    @Override
    public void tick() {
        super.tick();
        super.getStrategy().tick(this);
    }


}
