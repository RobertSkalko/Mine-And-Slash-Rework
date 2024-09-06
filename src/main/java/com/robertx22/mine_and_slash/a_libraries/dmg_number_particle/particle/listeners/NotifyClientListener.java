package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.listeners;

import com.google.common.eventbus.Subscribe;
import com.robertx22.mine_and_slash.event.server.NotifyClientToSpawnParticleEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NotifyClientListener {
    @Subscribe
    public void notifyClient(NotifyClientToSpawnParticleEvent event){

    }
}
