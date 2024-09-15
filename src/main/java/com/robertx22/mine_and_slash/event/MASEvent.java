package com.robertx22.mine_and_slash.event;

import com.google.common.eventbus.EventBus;

public class MASEvent extends EventBus {

    public static final MASEvent INSTANCE = new MASEvent();

    public MASEvent() {
        super("mine_and_slash");
    }
}
