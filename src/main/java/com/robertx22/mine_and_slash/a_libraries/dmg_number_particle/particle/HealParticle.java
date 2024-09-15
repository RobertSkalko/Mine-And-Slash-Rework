package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;

public class HealParticle extends ExileSpellResultParticle{
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
    public void render(VertexConsumer vertexConsumer, Camera camera, float v) {

    }
}
