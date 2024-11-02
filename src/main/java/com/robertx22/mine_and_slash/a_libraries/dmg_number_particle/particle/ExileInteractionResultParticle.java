package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ExileInteractionResultParticle extends Particle{
    private static final ParticleGroup GROUP = new ParticleGroup(1000);

    private final IParticleRenderStrategy strategy;


    protected ExileInteractionResultParticle(ClientLevel clientLevel, double x, double y, double z, IParticleRenderStrategy strategy) {
        super(clientLevel, x, y, z);
        this.strategy = strategy;
        this.strategy.setupStyle(this);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public IParticleRenderStrategy getStrategy() {
        return strategy;
    }


    public Vec3 getOriginalPosition(){
        return new Vec3(this.xo, this.yo, this.z);
    }

    protected int getAge(){
        return age;
    }

    protected int getLiftTime(){
        return  lifetime;
    }

    public void setLiftTime(int life){
        this.lifetime = life;
    }

    public void setGravity(float gravity){
        this.gravity = gravity;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    public @NotNull Optional<ParticleGroup> getParticleGroup() {
        return Optional.of(GROUP);
    }

}
