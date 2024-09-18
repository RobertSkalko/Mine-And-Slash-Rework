package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;

public class HealParticle extends ExileInteractionResultParticle {
    private final float amount;

    public HealParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, float amount) {
        super(clientLevel, x, y, z, strategy);
        this.amount = amount;
    }

    @Override
    protected int getColor() {
        return ChatFormatting.GREEN.getColor();
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupStyle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderHeal(this, vertexConsumer, camera, partialTick, posestack, amount + "", getColor());
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    public record HealNumber(float number) implements IParticleSpawnNotifier {
        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeFloat(number);
        }

        @Override
        public IParticleSpawnNotifier loadFromData(FriendlyByteBuf friendlyByteBuf) {
            return new HealNumber(friendlyByteBuf.readFloat());
        }

        @Override
        public SpellResultParticleSpawner.SpawnType getSpawnType() {
            return null;
        }
    }
}
