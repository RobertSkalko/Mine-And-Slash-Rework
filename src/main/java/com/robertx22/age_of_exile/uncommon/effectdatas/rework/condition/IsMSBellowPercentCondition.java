package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsMSBellowPercentCondition extends StatCondition {

    EffectSides side;
    int perc;

    public IsMSBellowPercentCondition(String id, int percent, EffectSides side) {
        super(id, "is_ms_under");
        this.side = side;
        this.perc = percent;
    }

    IsMSBellowPercentCondition() {
        super("", "is_ms_under");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        EntityData en = Load.Unit(event.getSide(side));
        return perc > en.getResources().getMagicShield() / en.getUnit().magicShieldData().getValue() * 100F;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsMSBellowPercentCondition.class;
    }

}
