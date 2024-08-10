package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class CancelEvent extends StatEffect {

    public CancelEvent() {
        super("cancel_event", "cancel_event");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.setBoolean(EventData.CANCELED, true);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return CancelEvent.class;
    }
}