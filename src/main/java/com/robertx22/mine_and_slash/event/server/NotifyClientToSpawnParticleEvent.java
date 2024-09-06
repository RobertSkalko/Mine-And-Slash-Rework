package com.robertx22.mine_and_slash.event.server;

public class NotifyClientToSpawnParticleEvent {
    public Type type;

    public NotifyClientToSpawnParticleEvent(Type type) {
        this.type = type;
    }

    public enum Type{
        DAMAGE,
        NULLIFIED_DAMAGE,
        HEAL
    }
}
