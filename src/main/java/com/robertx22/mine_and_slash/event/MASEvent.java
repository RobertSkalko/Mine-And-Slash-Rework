package com.robertx22.mine_and_slash.event;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MASEvent extends AsyncEventBus {

    public static final MASEvent INSTANCE = new MASEvent();

    private final static ExecutorService thread = Executors.newFixedThreadPool(1);

    public MASEvent() {
        super("mine_and_slash", thread);
    }
}
