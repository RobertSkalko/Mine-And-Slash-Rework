package com.robertx22.mine_and_slash.aoe_data.database.ailments;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class AilmentSpeed extends Stat {
    public static AilmentSpeed INSTANCE = new AilmentSpeed();

    public AilmentSpeed() {
        this.max = 200;
        this.min = -75;
    }

    @Override
    public Elements getElement() {
        return Elements.ALL;
    }

    @Override
    public String locDescForLangFile() {
        return "Your DOT ailments (burn, poison, bleed) now deal damage faster but also expire faster.";
    }

    @Override
    public String locNameForLangFile() {
        return "Damage Over Time Speed";
    }

    @Override
    public String GUID() {
        return "dot_speed";
    }
}
