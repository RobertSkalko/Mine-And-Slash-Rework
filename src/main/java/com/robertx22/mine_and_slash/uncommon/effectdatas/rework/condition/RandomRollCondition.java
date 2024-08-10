package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class RandomRollCondition extends StatCondition {

    public RandomRollCondition() {
        super("random_roll", "random_roll");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return RandomUtils.roll(data.getValue());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return RandomRollCondition.class;
    }

}
