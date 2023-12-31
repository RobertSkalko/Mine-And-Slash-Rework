package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SpellParticlePacket extends MyPacket<SpellParticlePacket> {
    private MyPosition pos;
    private ParticleOptions particle;
    private MyPosition vel;
    private Boolean hide_in_fx = false;

    public SpellParticlePacket(MyPosition pos, ParticleOptions particle, MyPosition vel, Boolean hide_in_fx) {
        this.pos = pos;
        this.particle = particle;
        this.vel = vel;
        this.hide_in_fx = hide_in_fx;
    }

    public SpellParticlePacket(){
    }
    @Override
    public ResourceLocation getIdentifier()  {
        return new ResourceLocation(SlashRef.MODID, "spellparticle");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.pos = new MyPosition(buf.readDouble(), buf.readDouble(), buf.readDouble());
        ParticleType<?> particletype = buf.readById(BuiltInRegistries.PARTICLE_TYPE);
        this.particle = this.readParticle(buf, particletype);
        this.vel = new MyPosition(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.hide_in_fx = buf.readBoolean();
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeDouble(this.pos.x);
        buf.writeDouble(this.pos.y);
        buf.writeDouble(this.pos.z);
        buf.writeId(BuiltInRegistries.PARTICLE_TYPE, this.particle.getType());
        buf.writeDouble(this.vel.x);
        buf.writeDouble(this.vel.y);
        buf.writeDouble(this.vel.z);
        buf.writeBoolean(this.hide_in_fx);

    }

    @Override
    public void onReceived(ExilePacketContext exilePacketContext) {
        if (!ClientConfigs.getConfig().ENABLE_PHOTON_FX.get() || !hide_in_fx) {
            exilePacketContext.getPlayer().level().addParticle(particle, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
        }
    }

    @Override
    public MyPacket<SpellParticlePacket> newInstance() {
        return new SpellParticlePacket();
    }

    private <T extends ParticleOptions> T readParticle(FriendlyByteBuf buf, ParticleType<T> pt) {
        return pt.getDeserializer().fromNetwork(pt, buf);
    }
}
