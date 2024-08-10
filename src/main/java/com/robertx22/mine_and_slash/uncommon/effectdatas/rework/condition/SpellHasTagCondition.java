package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class SpellHasTagCondition extends StatCondition {

    SpellTag tag;

    public SpellHasTagCondition(SpellTag tag) {
        super("spell_has_tag_" + tag.GUID(), "spell_has_tag");
        this.tag = tag;
    }

    SpellHasTagCondition() {
        super("", "spell_has_tag");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.isSpell()) {
            return event.getSpell().is(tag);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return SpellHasTagCondition.class;
    }

}
