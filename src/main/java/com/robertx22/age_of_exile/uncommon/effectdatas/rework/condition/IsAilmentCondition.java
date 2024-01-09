package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

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
