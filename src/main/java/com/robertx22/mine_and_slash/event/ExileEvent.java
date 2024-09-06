package com.robertx22.mine_and_slash.event;

import com.google.common.eventbus.EventBus;

public class ExileEvent extends EventBus {

    public static final ExileEvent INSTANCE = new ExileEvent();

    public ExileEvent() {
        super("mine_and_slash");
    }
}
