package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.listeners;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.event.server.TriggerInteractionResultEvent;
import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.ExileInteractionResultPacket;
public class NotifyClientListener {

    @AllowConcurrentEvents
    @Subscribe
    public void notifyClient(TriggerInteractionResultEvent event) {
        System.out.println("launch event!");
        Packets.sendToClient(event.source, new ExileInteractionResultPacket(event.target.getId(), event.notifier));

    }
}
