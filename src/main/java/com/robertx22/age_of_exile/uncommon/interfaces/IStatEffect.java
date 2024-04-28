package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;

public interface IStatEffect {


    public boolean worksOnEvent(EffectEvent ev);

    public abstract EffectSides Side();

    public abstract StatPriority GetPriority();

    public abstract void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat);

}
