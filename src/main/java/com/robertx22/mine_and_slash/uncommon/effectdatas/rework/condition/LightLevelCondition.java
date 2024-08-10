package com.robertx22.mine_and_slash.uncommon.effectdatas.rework.condition;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.components.NumberComparison;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class LightLevelCondition extends StatCondition {

    NumberComparison comparison;

    public LightLevelCondition(String id, NumberComparison comp) {
        super(id, "light_level");
        this.comparison = comp;
    }

    public LightLevelCondition() {
        super("", "light_level");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        int light = event.source.level().getMaxLocalRawBrightness(event.source.blockPosition());
        return comparison.is(light);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return LightLevelCondition.class;
    }

}
