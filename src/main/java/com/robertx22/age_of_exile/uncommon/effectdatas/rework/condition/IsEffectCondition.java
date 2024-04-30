package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsEffectCondition extends StatCondition {

    String effect;

    public IsEffectCondition(EffectCtx effect) {
        super("effect_has_tag_" + effect.GUID(), "is_effect");
        this.effect = effect.GUID();
    }

    IsEffectCondition() {
        super("", "is_effect");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.data.hasExileEffect()) {
            return event.data.getExileEffect().GUID().equals(effect);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsEffectCondition.class;
    }

}
