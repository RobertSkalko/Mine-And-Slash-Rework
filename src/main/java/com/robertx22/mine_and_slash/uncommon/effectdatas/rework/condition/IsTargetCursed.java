package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.all.EffectTags;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

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
