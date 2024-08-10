package com.robertx22.mine_and_slash.uncommon.effectdatas;

import net.minecraft.world.entity.LivingEntity;

public class TenSecondPlayerTickEvent extends EffectEvent {

    public static String ID = "player_tick_event_10s";

    public TenSecondPlayerTickEvent(LivingEntity source, LivingEntity target) {
        super(0, source, target);
    }

    @Override
    public String getName() {
        return "10s Tick Event";
    }

    @Override
    protected void activate() {

    }

    @Override
    public String GUID() {
        return ID;
    }
}
