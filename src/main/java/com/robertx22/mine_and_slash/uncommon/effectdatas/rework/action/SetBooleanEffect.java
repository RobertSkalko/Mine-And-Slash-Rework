package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.action;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class SetBooleanEffect extends StatEffect {

    String bool_id = "";
    Boolean bool = true;

    public SetBooleanEffect(String boolId) {
        super("set_bool_" + boolId, "set_bool");
        this.bool_id = boolId;
    }

    SetBooleanEffect() {
        super("", "set_bool");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.setBoolean(bool_id, bool);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return SetBooleanEffect.class;
    }

}
