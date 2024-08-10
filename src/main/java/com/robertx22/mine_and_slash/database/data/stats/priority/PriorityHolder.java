package com.robertx22.mine_and_slash.database.data.stats.priority;

import com.google.common.base.Preconditions;

public class PriorityHolder {
    String event;
    StatPriority prio;

    public PriorityHolder(String event, StatPriority prio) {
        this.event = event;
        this.prio = prio;
        Preconditions.checkArgument(prio.event.equals(event));
    }
}
