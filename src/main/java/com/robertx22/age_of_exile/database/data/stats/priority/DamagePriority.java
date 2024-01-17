package com.robertx22.age_of_exile.database.data.stats.priority;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;

public class DamagePriority extends PriorityHolder {

    public DamagePriority(StatPriority prio) {
        super(DamageEvent.ID, prio);
    }
}
