package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public abstract class ExileNumberParticlePacket extends MyPacket<ExileNumberParticlePacket> {

    public static Object2ObjectMap<ResourceLocation, Supplier<ExileNumberParticlePacket>> map = new Object2ObjectArrayMap<>();
    public static void registerAll(){
        new ElementDamageParticlePacket().register();
    }

    public void register() {
        map.put(this.getIdentifier(), () -> this);
    }

    public abstract String spawnParticle(Vec3 position);
}
