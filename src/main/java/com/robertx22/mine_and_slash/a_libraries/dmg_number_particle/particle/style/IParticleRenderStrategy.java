package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileSpellResultParticle;
import net.minecraft.client.Camera;

import java.util.function.Supplier;

public interface IParticleRenderStrategy {
    

    void setupStyle(ExileSpellResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack);

    void renderDamage(ExileSpellResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color);

    void renderNullifiedDamage(ExileSpellResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color);

    float scaleChange(ExileSpellResultParticle particle, float partialTick);

}
