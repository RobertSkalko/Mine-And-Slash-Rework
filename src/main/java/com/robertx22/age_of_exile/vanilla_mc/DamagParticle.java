package com.robertx22.age_of_exile.vanilla_mc;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;

public class DamagParticle extends TextureSheetParticle {
    protected DamagParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);

    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        super.render(pBuffer, pRenderInfo, pPartialTicks);

        
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }
}
