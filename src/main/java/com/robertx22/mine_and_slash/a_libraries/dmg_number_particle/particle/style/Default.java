package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.RenderUtils;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.DamageNullifiedParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl.HealParticle;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Random;

public class Default implements IParticleRenderStrategy {

    private float scale = 1.0f;

    protected static boolean DEFAULTParticleSpeedDirection = true;
    @Override
    public void setupStyle(ExileInteractionResultParticle particle) {
        particle.setGravity(0.8f);
        Random random = new Random();
        double v = random.nextDouble(0, 0.15);
        v = DEFAULTParticleSpeedDirection ? v : -v;
        DEFAULTParticleSpeedDirection = !DEFAULTParticleSpeedDirection;
        particle.setParticleSpeed(0 + v, 0.1 + random.nextDouble(0.2, 0.3), 0);
        particle.setLiftTime(15 + Minecraft.getInstance().player.level().random.nextInt(5));
    }

    @Override
    public void tick(ExileInteractionResultParticle particle) {

    }

    @Override
    public void setupParticle(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack) {
        Vec3 cameraPos = camera.getPosition();
        Vec3 pos = particle.getPos();
        Vec3 original = particle.getOriginalPosition();
        double x = (float) (Mth.lerp(partialTick, original.x, pos.x));
        double y = (float) (Mth.lerp(partialTick, original.y, pos.y));
        double z = (float) (Mth.lerp(partialTick, original.z, pos.z));
        float scale = this.scale * 0.02F;
        Vec3 vec3 = limitDistance(new Vec3(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z));
        posestack.translate(vec3.x(), vec3.y(), vec3.z());
        posestack.mulPose(camera.rotation());
        posestack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        posestack.scale(scale, scale, scale);
        posestack.translate(0.0F, -2.0F, 0.0F);
    }

    @Override
    public void renderDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        IParticleRenderMaterial.singleElement material = (IParticleRenderMaterial.singleElement) mat;
        boolean crit = material.isCrit();
        Pair<Elements, String> mat1 = material.getMat();
        String value = mat1.getValue();
        ChatFormatting format = mat1.getKey().format;
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(value) / 2);

        RenderUtils.renderText(posestack, crit ? value + "!" : value, f, format.getColor(), multibuffersource$buffersource);
    }

    @Override
    public void renderNullifiedDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        IParticleRenderMaterial.simpleText material = (IParticleRenderMaterial.simpleText) mat;
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(material.getMat()) / 2);

        RenderUtils.renderText(posestack, material.getMat(),f, DamageNullifiedParticle.color, multibuffersource$buffersource);

    }

    @Override
    public void renderHeal(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        IParticleRenderMaterial.simpleText material = (IParticleRenderMaterial.simpleText) mat;
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        float f = (float) (-Minecraft.getInstance().font.width(material.getMat()) / 2);

        RenderUtils.renderText(posestack, material.getMat(),f, HealParticle.color, multibuffersource$buffersource);
    }


    @Override
    public float changeScale(ExileInteractionResultParticle particle, int age, int lifeTime, float partialTick) {
        return 0;
    }

    private static Vec3 limitDistance(Vec3 pos){
        double maxDistance = 6.0d;
        if (pos.length() >= maxDistance){
            Vec3 normalize = pos.normalize();
            return normalize.scale(maxDistance);
        }
        return pos;
    }

}
