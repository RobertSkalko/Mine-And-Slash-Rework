package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle;

import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.world.entity.Entity;

public class DamageParticleAdder {

    public static void displayParticle(Entity entity, DmgNumPacket packet) {

        DamageParticleRenderer.PARTICLES.add(new DamageParticle(entity, packet));

    }

}
