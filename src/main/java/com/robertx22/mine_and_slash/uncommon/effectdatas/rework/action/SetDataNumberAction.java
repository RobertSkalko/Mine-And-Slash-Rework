package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class SetDataNumberAction extends StatEffect {

    public String num_id = "";

    public SetDataNumberAction(String num_id) {
        super("set_data_num_" + num_id, "set_data_number");
        this.num_id = num_id;
    }

    public SetDataNumberAction() {
        super("", "set_data_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number = data.getValue();
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return SetDataNumberAction.class;
    }
}
