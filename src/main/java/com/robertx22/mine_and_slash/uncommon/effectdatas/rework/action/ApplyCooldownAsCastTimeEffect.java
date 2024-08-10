package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class ApplyCooldownAsCastTimeEffect extends StatEffect {

    public ApplyCooldownAsCastTimeEffect() {
        super("apply_cd_as_cast_time", "apply_cd_as_cast_time");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(EventData.COOLDOWN_TICKS).number -= event.data.getOriginalNumber(EventData.COOLDOWN_TICKS).number * data.getValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ApplyCooldownAsCastTimeEffect.class;
    }
}