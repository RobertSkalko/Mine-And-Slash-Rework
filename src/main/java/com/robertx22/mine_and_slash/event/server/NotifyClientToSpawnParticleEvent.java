package com.robertx22.mine_and_slash.event.server;

import com.robertx22.mine_and_slash.vanilla_mc.packets.interaction.IParticleSpawnNotifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class NotifyClientToSpawnParticleEvent {

    public final IParticleSpawnNotifier notifier;
    public final ServerPlayer source;
    public final LivingEntity target;

    public NotifyClientToSpawnParticleEvent(IParticleSpawnNotifier notifier, ServerPlayer source, LivingEntity target) {
        this.notifier = notifier;
        this.source = source;
        this.target = target;
    }
}
