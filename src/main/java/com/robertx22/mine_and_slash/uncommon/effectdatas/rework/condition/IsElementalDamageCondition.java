package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.all.ElementTags;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IsElementalDamageCondition extends StatCondition {
    public IsElementalDamageCondition() {
        super("is_elemental_damage", "is_elemental_damage");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.data.getElement().tags.contains(ElementTags.ELEMENTAL);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsElementalDamageCondition.class;
    }
}
