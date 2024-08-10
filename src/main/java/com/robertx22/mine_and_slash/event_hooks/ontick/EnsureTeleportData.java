package com.robertx22.mine_and_slash.event_hooks.ontick;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.BlockPos;

import java.util.function.Consumer;

public class EnsureTeleportData {

    ServerPlayer player;
    Consumer<EnsureTeleportData> action;
    int ticksLeft;
    int ticks = 0;
    BlockPos whereShouldTeleport;
    int tries = 0;

    int origTicksLeft;

    public EnsureTeleportData(ServerPlayer player, Consumer<EnsureTeleportData> action, int ticksLeft, BlockPos whereShouldTeleport) {
        this.player = player;
        this.action = action;
        this.ticksLeft = ticksLeft;
        this.whereShouldTeleport = whereShouldTeleport;

        this.origTicksLeft = ticksLeft;
    }

    public void cancel() {
        this.ticksLeft = -1;
    }

    public void resetTicks() {
        this.ticks = 0;
        this.ticksLeft = origTicksLeft;
    }
}
