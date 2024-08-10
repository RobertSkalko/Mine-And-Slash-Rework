package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.ModTag;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class EffectHasTagCondition extends StatCondition {

    String tag;

    public EffectHasTagCondition(ModTag tag) {
        super("effect_has_tag_" + tag.GUID(), "effect_has_tag");
        this.tag = tag.GUID();
    }

    EffectHasTagCondition() {
        super("", "effect_has_tag");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.data.hasExileEffect()) {
            return event.data.getExileEffect().hasTag(tag);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return EffectHasTagCondition.class;
    }

}
