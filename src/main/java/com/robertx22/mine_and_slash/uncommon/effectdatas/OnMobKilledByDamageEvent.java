package com.robertx22.mine_and_slash.uncommon.effectdatas;

public class OnMobKilledByDamageEvent extends EffectEvent {

    public static String ID = "on_mob_kill";

    @Override
    public String GUID() {
        return ID;
    }

    public DamageEvent damageEvent;

    public OnMobKilledByDamageEvent(DamageEvent damageEvent) {
        super(damageEvent.source, damageEvent.target);
        this.damageEvent = damageEvent;

        this.data = damageEvent.data;
    }

    @Override
    public String getName() {
        return "Kill Event";
    }

    @Override
    public boolean canWorkOnDeadTarget() {
        return true;
    }

    @Override
    protected void activate() {

    }
}

