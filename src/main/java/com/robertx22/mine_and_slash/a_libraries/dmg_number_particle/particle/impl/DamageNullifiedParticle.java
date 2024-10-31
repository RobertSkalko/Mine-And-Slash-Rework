package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderMaterial;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class DamageNullifiedParticle extends ExileInteractionResultParticle {
    private final IParticleSpawnMaterial.Type type;

    public static final int color = ChatFormatting.WHITE.getColor();

    public DamageNullifiedParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, IParticleSpawnMaterial.Type type) {
        super(clientLevel, x, y, z, strategy);
        this.type = type;
    }



    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupParticle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderNullifiedDamage(this, vertexConsumer, camera, partialTick, posestack, new IParticleRenderMaterial.simpleText(type.text.getString()));
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    @Override
    public void tick() {
        super.tick();
        super.getStrategy().tick(this);
    }

}
