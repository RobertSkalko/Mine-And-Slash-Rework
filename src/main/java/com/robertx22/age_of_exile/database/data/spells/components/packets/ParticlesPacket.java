package com.robertx22.age_of_exile.database.data.spells.components.packets;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.registry.serialization.MyGSON;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.geometry.Circle3d;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.library_of_exile.utils.geometry.ShapeHelper;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ParticlesPacket extends MyPacket<ParticlesPacket> {

    public Data data = new Data();

    public ParticlesPacket(Data data) {
        this.data = data;
    }

    public static class Data {


        public Vec3 pos = new Vec3(0, 0, 0);
        public Vec3 vel = new Vec3(0, 0, 0);
        public Vec3 casterAngle = new Vec3(0, 0, 0);


        public float height = 0;
        public float yrand = 0;
        public float radius = 1;

        public String particle = "";
        public ParticleMotion motion = ParticleMotion.None;
        public ParticleShape shape = ParticleShape.CIRCLE;
        public int amount = 1;
        public float motionMulti = 1;

        public SimpleParticleType getParticle() {
            return (SimpleParticleType) BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(particle));
        }
    }


    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("particles");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        data = MyGSON.GSON.fromJson(buf.readUtf(), Data.class);
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        buf.writeUtf(MyGSON.GSON.toJson(data));
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        if (this.data.amount > ClientConfigs.getConfig().DONT_CULL_PARTICLES_UNDER.get()) {
            this.data.amount *= ClientConfigs.getConfig().SPELL_PARTICLE_MULTI.get();
        }

        Player p = ctx.getPlayer();
        Level world = p.level();

        var particle = data.getParticle();

        MyPosition middle = new MyPosition(data.pos);

        Vec3 vel = data.vel;

        ShapeHelper c = new Circle3d(new MyPosition(data.pos), data.radius);


        c.doXTimes(data.amount, x -> {

            MyPosition sp = data.shape.getPosition(middle, data.radius, x.multi);

            float yRandom = (int) RandomUtils.RandomRange(0, data.yrand);

            sp = new MyPosition(sp.x - vel.x / 2F, sp.y - vel.y / 2 + data.height, sp.z - vel.z / 2);

            Vec3 v = data.motion.getMotion(new Vec3(sp.x, sp.y + yRandom, sp.z), data.casterAngle, data.pos).multiply(data.motionMulti, data.motionMulti, data.motionMulti);

            c.spawnParticle(world, sp.asVector3D(), particle, new MyPosition(v).asVector3D());
        });
    }

    @Override
    public MyPacket<ParticlesPacket> newInstance() {
        return new ParticlesPacket(new Data());
    }
}
