package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
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
        System.out.println("rendering damage particle!");
        super.getStrategy().setupStyle(this, vertexConsumer, camera, partialTick, posestack);
        super.getStrategy().renderDamage(this, vertexConsumer, camera, partialTick, posestack, damageString, getColor());

        posestack.popPose();
    }


    public record DamageInformation(byte[] elements, FloatList damage,
                                    boolean isCrit) implements IParticleSpawnNotifier {
        public static DamageInformation fromDmgByElement(DamageEvent.DmgByElement mat, boolean isCrit){
            HashMap<Elements, Float> dmgmap = mat.getDmgmap();
            System.out.println("the DmgByElement map is " + dmgmap);
            int size = dmgmap.size();
            byte[] bytes = new byte[size];
            AtomicInteger i = new AtomicInteger(0);
            FloatArrayList floats = new FloatArrayList();
            dmgmap.forEach((key, value1) -> {
                float value = value1;
                bytes[i.getAndIncrement()] = ((byte) key.ordinal());
                floats.add(value);
            });
            //System.out.println(Arrays.toString(bytes));
            //System.out.println(floats);
            return new DamageInformation(bytes, floats, isCrit);
        }
        public ImmutableMap<Elements, Float> getDmgMap() {
            System.out.println("start getDmgMap()");
            ImmutableMap.Builder<Elements, Float> builder = ImmutableMap.builder();
            for (int i = 0; i < elements.length; i++) {
                System.out.println("elements[i] is " + elements[i]);
                System.out.println("the enum is " + Elements.values()[elements[i]]);
                builder.put(Elements.values()[elements[i]], damage.getFloat(i));
            }
            return builder.build();
        }

        @Override
        public void saveToBuf(FriendlyByteBuf friendlyByteBuf) {
            System.out.println("the elements is " + Arrays.toString(elements));
            friendlyByteBuf.writeByteArray(elements);
            friendlyByteBuf.writeCollection(damage, (FriendlyByteBuf::writeFloat));
            friendlyByteBuf.writeBoolean(isCrit);

        }

        @Override
        public DamageInformation loadFromData(FriendlyByteBuf friendlyByteBuf) {
            byte[] bytes = friendlyByteBuf.readByteArray();
            System.out.println("the byte array is " + Arrays.toString(bytes));
            return new DamageInformation(bytes, friendlyByteBuf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat), friendlyByteBuf.readBoolean());

        }

        @Override
        public SpellResultParticleSpawner.SpawnType getSpawnType() {
            return SpellResultParticleSpawner.SpawnType.DAMAGE;
        }


    }

}
