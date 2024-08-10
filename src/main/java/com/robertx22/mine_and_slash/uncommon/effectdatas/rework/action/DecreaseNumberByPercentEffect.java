package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class DecreaseNumberByPercentEffect extends StatEffect {

    String num_id = "";

    public DecreaseNumberByPercentEffect(String num) {
        super("decrease_" + num + "_num", "decrease_num");
        this.num_id = num;
    }

    DecreaseNumberByPercentEffect() {
        super("", "decrease_num");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number -= event.data.getOriginalNumber(num_id).number * data.getValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return DecreaseNumberByPercentEffect.class;
    }
}