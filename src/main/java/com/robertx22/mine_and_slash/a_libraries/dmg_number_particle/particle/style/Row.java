package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
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

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (isCrit) {
            text = text.replace("!", "");
        }
        float startFrom = 0.0f;
        String[] split = text.split("(?=§)");
        ArrayList<Pair<String, String>> colorAndNumber = new ArrayList<>();
        Function<String, String> damageFormat = (number) -> number + " ";
        for (String string : split) {
            Pattern compile = Pattern.compile("(§.)(.+)");
            Matcher matcher = compile.matcher(string);

            if (matcher.find()) {
                String colorCode = matcher.group(1);
                String number = matcher.group(2);
                colorAndNumber.add(Pair.of(colorCode, number));
                int i = -Minecraft.getInstance().font.width(damageFormat.apply(number));
                startFrom += i;

            }

        }
        startFrom = startFrom / 2;
        float usedWidth = 0;
        for (Pair<String, String> stringStringPair : colorAndNumber) {
            String code = stringStringPair.getLeft();
            String number = stringStringPair.getRight();
            String damage = damageFormat.apply(number);


            ChatFormatting byCode = ChatFormatting.getByCode(code.charAt(1));
            MutableComponent mutableComponent = MutableComponent.create(new LiteralContents(damage)).withStyle(byCode);
            if (isCrit){
                mutableComponent.withStyle(ChatFormatting.BOLD);
            }
            int thisWidth = Minecraft.getInstance().font.width(mutableComponent);
            Minecraft.getInstance().font.drawInBatch(mutableComponent, startFrom + usedWidth, 0.0F, Optional.ofNullable(byCode.getColor()).orElseGet(ChatFormatting.GRAY::getColor), false, posestack.last().pose(), multibuffersource$buffersource, Font.DisplayMode.SEE_THROUGH, 0, 15728880);
            usedWidth += thisWidth;
            multibuffersource$buffersource.endBatch();

        }


    }

    @Override
    public void renderNullifiedDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {
        new Original().renderNullifiedDamage(particle, vertexConsumer, camera, partialTick, posestack, text, color);
    }

    @Override
    public void renderHeal(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {
        new Original().renderHeal(particle, vertexConsumer, camera, partialTick, posestack, text, color);
    }

    @Override
    public float changeScale(ExileInteractionResultParticle particle, int age, int lifeTime, float partialTick) {
        return 0;
    }


}
