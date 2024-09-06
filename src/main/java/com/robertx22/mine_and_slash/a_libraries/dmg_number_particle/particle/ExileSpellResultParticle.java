package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ExileSpellResultParticle extends Particle{
    private static final ParticleGroup GROUP = new ParticleGroup(1000);
    private float scale;
    private float prevScale;

    private final IParticleRenderStrategy strategy = new Original();


    protected ExileSpellResultParticle(ClientLevel clientLevel, double x, double y, double z) {
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

    public IParticleRenderStrategy getStrategy() {
        return strategy;
    }

    protected abstract int getColor();

    public float getScale(float partialTicks) {
        return prevScale + (scale - prevScale) * partialTicks;
    }

    public Vec3 getOriginalPosition(){
        return new Vec3(this.xo, this.yo, this.z);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public @NotNull Optional<ParticleGroup> getParticleGroup() {
        return Optional.of(GROUP);
    }

}
