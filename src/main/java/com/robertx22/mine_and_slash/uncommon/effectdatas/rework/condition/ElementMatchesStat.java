package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class ElementMatchesStat extends StatCondition {

    public ElementMatchesStat() {
        super("ele_match_stat", "ele_match_stat");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.data.getElement().elementsMatch(stat.getElement());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return ElementMatchesStat.class;
    }

}
