package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IsSpellCondition extends StatCondition {

    public IsSpellCondition() {
        super("is_spell", "is_spell");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.isSpell();
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsSpellCondition.class;
    }

}
