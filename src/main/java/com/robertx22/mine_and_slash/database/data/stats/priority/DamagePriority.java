package com.robertx22.mine_and_slash.database.data.stats.priority;

import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;

public class DamagePriority extends PriorityHolder {

    public DamagePriority(StatPriority prio) {
        super(DamageEvent.ID, prio);
    }
}
