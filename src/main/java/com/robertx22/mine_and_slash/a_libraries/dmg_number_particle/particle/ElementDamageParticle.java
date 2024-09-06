package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;

public class ElementDamageParticle extends ExileSpellResultParticle{
    private final int color;
    private final String damageString;


    protected ElementDamageParticle(ClientLevel clientLevel, double x, double y, double z, String damageText, int color) {
        super(clientLevel, x, y, z);
        this.color = color;
        this.damageString = damageText;
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



    public record DamageInformation(byte[] elements, FloatList damage){
        public void writeIntoBuf(FriendlyByteBuf buf){
            buf.writeBytes(elements);
            buf.writeCollection(damage, (FriendlyByteBuf::writeFloat));
        }

        public DamageInformation readIntoBuf(FriendlyByteBuf buf){
            return new DamageInformation(buf.readByteArray(), buf.readCollection(FloatArrayList::new, FriendlyByteBuf::readFloat));
        }

        public ImmutableMap<Elements, Float> getDmgMap(){
            ImmutableMap.Builder<Elements, Float> builder = ImmutableMap.builder();
            for (int i = 0; i < elements.length; i++) {
                builder.put(Elements.values()[elements[i]], damage.getFloat(i));
            }
            return builder.build();
        }
    }

}
