package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IsUndeadCondition extends StatCondition {

    public IsUndeadCondition() {
        super("is_undead", "is_undead");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.target.isInvertedHealAndHarm();
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsUndeadCondition.class;
    }

}
