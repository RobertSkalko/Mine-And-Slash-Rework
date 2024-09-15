package com.robertx22.mine_and_slash.vanilla_mc.packets.interaction;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.SpellResultParticleSpawner;
import net.minecraft.network.FriendlyByteBuf;

public interface IParticleSpawnNotifier {

    void saveToBuf(FriendlyByteBuf friendlyByteBuf);

    IParticleSpawnNotifier loadFromData(FriendlyByteBuf friendlyByteBuf);

    SpellResultParticleSpawner.SpawnType getSpawnType();

}
