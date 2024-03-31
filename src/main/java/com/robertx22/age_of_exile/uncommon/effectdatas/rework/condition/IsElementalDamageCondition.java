package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.tags.all.ElementTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

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
