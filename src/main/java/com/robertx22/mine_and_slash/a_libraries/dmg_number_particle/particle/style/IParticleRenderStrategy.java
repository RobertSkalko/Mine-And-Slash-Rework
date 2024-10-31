package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import net.minecraft.client.Camera;

public interface IParticleRenderStrategy {

    void setupStyle(ExileInteractionResultParticle particle);

    void tick(ExileInteractionResultParticle particle);

    void setupParticle(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack);

    void renderDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial material);

    void renderNullifiedDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial material);

    void renderHeal(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial material);


    float changeScale(ExileInteractionResultParticle particle, int age, int lifeTime, float partialTick);

}
