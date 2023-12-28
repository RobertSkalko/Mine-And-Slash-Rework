package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.tags.all.EffectTags;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsTargetCursed extends StatCondition {

    public IsTargetCursed() {
        super("is_target_cursed", "is_target_cursed");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.targetData.getStatusEffectsData().getEffects().stream().anyMatch(x -> x.hasTag(EffectTags.curse));
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsTargetCursed.class;
    }

}
