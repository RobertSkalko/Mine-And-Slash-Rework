package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class DamageNullifiedParticle extends ExileInteractionResultParticle {
    private final Type type;

    public DamageNullifiedParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, Type type) {
        super(clientLevel, x, y, z, strategy);
        this.type = type;
    }

    @Override
    protected int getColor() {
        return ChatFormatting.WHITE.getColor();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupStyle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderNullifiedDamage(this, vertexConsumer, camera, partialTick, posestack, type.text, getColor());
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    public enum Type implements IParticleSpawnNotifier {
        DODGE("dodge", SoundEvents.SHIELD_BLOCK),
        RESIST("resist", SoundEvents.SHIELD_BLOCK);

        public final String text;
        public final SoundEvent sound;

        Type(String text, SoundEvent sound) {
            this.text = text;
            this.sound = sound;
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeEnum(this);
        }

        @Override
        public IParticleSpawnNotifier loadFromData(FriendlyByteBuf friendlyByteBuf) {
            return friendlyByteBuf.readEnum(DamageNullifiedParticle.Type.class);
        }

        @Override
        public InteractionResultHandler.ParticleSpawnType getSpawnType() {
            return InteractionResultHandler.ParticleSpawnType.NULLIFIED_DAMAGE;
        }
    }
}
