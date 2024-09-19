package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ElementDamageParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wynn implements IParticleRenderStrategy{

    private final IParticleRenderStrategy original = new Original();
    @Override
    public void setupStyle(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack) {
        Vec3 cameraPos = camera.getPosition();
        Vec3 pos = particle.getPos();
        Vec3 original = particle.getOriginalPosition();
        double x = (float) (Mth.lerp((double) partialTick, original.x, pos.x));
        double y = (float) (Mth.lerp((double) partialTick, original.y, pos.y));
        double z = (float) (Mth.lerp((double) partialTick, original.z, pos.z));
        float scale = 0.02f;
        posestack.translate(x - cameraPos.x, y - cameraPos.y, z - cameraPos.z);
        posestack.mulPose(camera.rotation());
        posestack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        posestack.scale(scale, scale, scale);
        posestack.translate(0.0F, -2.0F, 0.0F);
    }

    @Override
    public void renderDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean isCrit = text.contains("!");
        if (isCrit){
            text = text.replace("!", "");
        }
        String[] split = text.split("(?=§)");
        for (String string : split) {
            float f = (float) (-Minecraft.getInstance().font.width(string) / 2);
            //have to use the Font.DisplayMode.SEE_THROUGH, otherwise it will be block by the health bar.
            Pattern compile = Pattern.compile("§(.)");
            Matcher matcher = compile.matcher(string);
            if (matcher.find()) {
                String code = matcher.group(1);
                ChatFormatting byCode = ChatFormatting.getByCode(code.charAt(0));
                Minecraft.getInstance().font.drawInBatch(string, f, 0.0F, byCode.getColor(), false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);
                multibuffersource$buffersource.endBatch();

            }

        }

    }

    @Override
    public void renderNullifiedDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {

    }

    @Override
    public void renderHeal(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {

    }

    @Override
    public float changeScale(ExileInteractionResultParticle particle, int age, int lifeTime, float partialTick) {
        return 0;
    }


}
