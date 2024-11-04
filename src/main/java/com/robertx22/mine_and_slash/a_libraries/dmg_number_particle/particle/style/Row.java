package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.RenderUtils;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class Row implements IParticleRenderStrategy {

    @Override
    public void setupStyle(ExileInteractionResultParticle particle) {
        particle.setGravity(0.0f);
        particle.setParticleSpeed(0, 0.06f, 0);
        particle.setLiftTime(20);
    }

    @Override
    public void tick(ExileInteractionResultParticle particle) {

    }

    @Override
    public void setupParticle(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack) {
        Vec3 cameraPos = camera.getPosition();
        Vec3 pos = particle.getPos();
        Vec3 original = particle.getOriginalPosition();
        double x = (float) (Mth.lerp((double) partialTick, original.x, pos.x));
        double y = (float) (Mth.lerp((double) partialTick, original.y, pos.y));
        double z = (float) (Mth.lerp((double) partialTick, original.z, pos.z));
        float scale = 0.02f;
        Vec3 vec3 = limitDistance(new Vec3(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z));
        posestack.translate(vec3.x(), vec3.y(), vec3.z());
        posestack.mulPose(camera.rotation());
        posestack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        posestack.scale(scale, scale, scale);
        posestack.translate(0.0F, -2.0F, 0.0F);
    }

    @Override
    public void renderDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        IParticleRenderMaterial.multipleElements material = (IParticleRenderMaterial.multipleElements)mat;

        List<Pair<Elements, String>> mat1 = material.getMat();
        boolean crit = material.isCrit();
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

        float startFrom = 0.0f;

        Function<String, String> damageFormat = (number) -> "-" + number + " ";
        for (Pair<Elements, String> elementsStringPair : mat1) {
            int i = -Minecraft.getInstance().font.width(damageFormat.apply(elementsStringPair.getValue()));
            startFrom += i;
        }

        startFrom = startFrom / 2;

        float usedWidth = 0;
        for (Pair<Elements, String> pair : mat1) {
            Elements element = pair.getLeft();
            String number = pair.getRight();
            String damage = damageFormat.apply(number);

            ChatFormatting format = element.format;
            MutableComponent mutableComponent = MutableComponent.create(new LiteralContents(damage)).withStyle(format);
            if (crit) {
                mutableComponent.withStyle(ChatFormatting.BOLD);
            }

            int thisWidth = Minecraft.getInstance().font.width(mutableComponent);
            RenderUtils.renderComponent(posestack, mutableComponent, startFrom + usedWidth, format.getColor(), multibuffersource$buffersource);

            usedWidth += thisWidth;
        }


    }

    @Override
    public void renderNullifiedDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        new Default().renderNullifiedDamage(particle, vertexConsumer, camera, partialTick, posestack, mat);
    }

    @Override
    public void renderHeal(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, IParticleRenderMaterial mat) {
        new Default().renderHeal(particle, vertexConsumer, camera, partialTick, posestack, mat);
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
