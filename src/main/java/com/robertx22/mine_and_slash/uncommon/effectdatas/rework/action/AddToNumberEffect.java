package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class AddToNumberEffect extends StatEffect {

    public String number_id;
    NumberProvider num_provider;

    public AddToNumberEffect(String id, String number_id, NumberProvider num_provider) {
        super(id, "add_to_number");
        this.num_provider = num_provider;
        this.number_id = number_id;
    }

    public AddToNumberEffect() {
        super("increase_number", "add_to_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(number_id).number += num_provider.getValue(event, event.getSide(statSource), data);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return AddToNumberEffect.class;
    }
}
