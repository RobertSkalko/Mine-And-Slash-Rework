package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;

import java.util.function.Supplier;

public interface IParticleRenderStrategy {
    
    void setupStyle(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack);

    void renderDamage(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, Supplier<?> extraInfo);

    void renderNullifiedDamage(VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, Supplier<?> extraInfo);

}
