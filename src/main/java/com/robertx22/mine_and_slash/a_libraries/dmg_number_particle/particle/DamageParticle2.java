package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class DamageParticle2 extends Particle {
    private static final ParticleGroup GROUP = new ParticleGroup(1000);
    private float scale;
    private float prevScale;

    protected DamageParticle2(ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z);
        this.lifetime = 15 + clientLevel.random.nextInt(5);
        this.scale = 1.0F;
        this.yd = 0.2F + Math.random() * 0.2F;
        this.gravity = 1.3F;
    }

    @Override
    public void tick() {
        super.tick();
        float ageScaled = age / (float) lifetime;
        this.prevScale = scale;
        this.scale = 1.0F - ageScaled;
    }



    protected abstract int getColor();

    protected float getScale(float partialTicks) {
        return prevScale + (scale - prevScale) * partialTicks;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public @NotNull Optional<ParticleGroup> getParticleGroup() {
        return Optional.of(GROUP);
    }

}
