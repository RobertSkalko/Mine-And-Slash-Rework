package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.impl;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionResultHandler;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnMaterial;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ElementDamageParticle extends ExileInteractionResultParticle {
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
        super.getStrategy().setupParticle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(this, vertexConsumer, camera, partialTick, posestack, damageString, getColor());
        super.getStrategy().changeScale(this, getAge(), getLiftTime(), partialTick);

        posestack.popPose();
    }

    @Override
    public void tick() {
        super.tick();
        super.getStrategy().tick(this);
    }

    public record DamageInformation(byte[] elements, FloatList damage,
                                    boolean isCrit) implements IParticleSpawnMaterial {
        public static DamageInformation fromDmgByElement(DamageEvent.DmgByElement mat, boolean isCrit){
            HashMap<Elements, Float> dmgmap = mat.getDmgmap();
            int size = dmgmap.size();
            byte[] bytes = new byte[size];
            AtomicInteger i = new AtomicInteger(0);
            FloatArrayList floats = new FloatArrayList();
            dmgmap.forEach((key, value1) -> {
                float value = value1;
                bytes[i.getAndIncrement()] = ((byte) key.ordinal());
                floats.add(value);
            });
            return new DamageInformation(bytes, floats, isCrit);
        }
        public ImmutableMap<Elements, Float> getDmgMap() {
            ImmutableMap.Builder<Elements, Float> builder = ImmutableMap.builder();
            for (int i = 0; i < elements.length; i++) {
                builder.put(Elements.values()[elements[i]], damage.getFloat(i));
            }
            return builder.build();
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            friendlyByteBuf.writeByteArray(elements);
            friendlyByteBuf.writeCollection(damage, (FriendlyByteBuf::writeFloat));
            friendlyByteBuf.writeBoolean(isCrit);

        }

        @Override
        public DamageInformation loadFromData(FriendlyByteBuf friendlyByteBuf) {
            byte[] bytes = friendlyByteBuf.readByteArray();
            return new DamageInformation(bytes, friendlyByteBuf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat), friendlyByteBuf.readBoolean());

        }

        @Override
        public InteractionResultHandler.ParticleSpawnType getSpawnType() {
            return InteractionResultHandler.ParticleSpawnType.DAMAGE;
        }


    }

}
