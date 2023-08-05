package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect.Priority;

public abstract class BaseSpecialStatDamageEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Last.priority;
    }
}
