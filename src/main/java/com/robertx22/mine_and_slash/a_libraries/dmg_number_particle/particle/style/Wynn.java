package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ElementDamageParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ExileInteractionResultParticle;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import net.minecraft.client.Camera;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Wynn implements IParticleRenderStrategy{

    private final IParticleRenderStrategy original = new Original();
    @Override
    public void setupStyle(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack) {
        original.setupStyle(particle, vertexConsumer, camera, partialTick, posestack);
    }

    @Override
    public void renderDamage(ExileInteractionResultParticle particle, VertexConsumer vertexConsumer, Camera camera, float partialTick, PoseStack posestack, String text, int color) {

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


    public record ElementString(ElementDamageParticle.DamageInformation info){
        public static ElementString fromString(String string){
            ArrayList<Byte> bytes = new ArrayList<>();
            FloatArrayList floats = new FloatArrayList();
            Pattern pattern = Pattern.compile("(.):(.+?)\\|");

        }

        public String toString(){
            ImmutableMap<Elements, Float> dmgMap = this.info.getDmgMap();
            StringBuilder string = new StringBuilder("|");
            dmgMap.forEach((k, v) -> {
                string.append(k).append(":").append(v).append("|");
            });
            string.append(this.info.isCrit());
            return string.toString();
        }
    }
}
