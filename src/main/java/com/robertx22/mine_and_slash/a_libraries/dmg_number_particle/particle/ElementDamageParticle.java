package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.Map;
import java.util.function.BiConsumer;

public class ElementDamageParticle extends ExileSpellResultParticle {
    private final int color;
    private final String damageString;

    public ElementDamageParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy, int color, String damageString) {
        super(clientLevel, x, y, z, strategy);
        this.color = color;
        this.damageString = damageString;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTick) {
        PoseStack posestack = new PoseStack();
        posestack.pushPose();

        super.getStrategy().setupStyle(vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(vertexConsumer, camera, partialTick, posestack, () -> new Original.TextAndColor(damageString, getColor()));

        posestack.popPose();
    }


    public record DamageInformation(byte[] elements, FloatList damage,
                                    boolean isCrit) implements IParticleSpawnNotifier<ElementDamageParticle> {


        public ImmutableMap<Elements, Float> getDmgMap() {
            ImmutableMap.Builder<Elements, Float> builder = ImmutableMap.builder();
            for (int i = 0; i < elements.length; i++) {
                builder.put(Elements.values()[elements[i]], damage.getFloat(i));
            }
            return builder.build();
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeBytes(elements);
            friendlyByteBuf.writeCollection(damage, (FriendlyByteBuf::writeFloat));
            friendlyByteBuf.writeBoolean(isCrit);

        }

        @Override
        public DamageInformation loadFromData(FriendlyByteBuf friendlyByteBuf) {
            return new DamageInformation(friendlyByteBuf.readByteArray(), friendlyByteBuf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat), friendlyByteBuf.readBoolean());

        }

        @Override
        public SpellResultParticleSpawner.SpawnType getSpawnType() {
            return SpellResultParticleSpawner.SpawnType.DAMAGE;
        }


    }

}
