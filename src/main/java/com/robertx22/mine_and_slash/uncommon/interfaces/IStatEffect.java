package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;

public interface IStatEffect {


    public boolean worksOnEvent(EffectEvent ev);

    public abstract EffectSides Side();

    public abstract StatPriority GetPriority();

    public abstract void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat);

}
