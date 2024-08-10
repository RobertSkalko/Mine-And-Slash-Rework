package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailment;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class IsAilmentCondition extends StatCondition {

    String ailment = "";

    public IsAilmentCondition(Ailment ailment) {
        super("is_ailment_" + ailment.GUID(), "is_ailment");
        this.ailment = ailment.GUID();
    }

    IsAilmentCondition() {
        super("", "is_ailment");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.data.getString(EventData.AILMENT).equals(ailment);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsAilmentCondition.class;
    }

}
