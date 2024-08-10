package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IncreaseNumberByPercentEffect extends StatEffect {

    String num_id = "";

    public IncreaseNumberByPercentEffect(String num) {
        super("increase_" + num + "_num", "increase_number");
        this.num_id = num;
    }

    IncreaseNumberByPercentEffect() {
        super("", "increase_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.increaseByPercent(num_id, data.getValue());
        event.data.getNumber(num_id).number = event.data.getNumber(num_id, 0).number * data.getMoreStatTypeMulti();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return IncreaseNumberByPercentEffect.class;
    }
}
