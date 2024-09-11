package com.robertx22.mine_and_slash.event.server;

import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.InteractionType;

public class NotifyClientToSpawnParticleEvent {
    public InteractionType type;

    public NotifyClientToSpawnParticleEvent(InteractionType type) {
        this.type = type;
    }

}
