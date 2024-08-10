package com.robertx22.mine_and_slash.database.data.profession.stat;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class DoubleDropChance extends Stat {

    String prof;

    public DoubleDropChance(String prof) {
        this.prof = prof;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance for profession to double the drop, if it happens";
    }


    private DoubleDropChance() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

    }

    @Override
    public String GUID() {
        return prof + "_double_drop";
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Double Drop Chance";
    }

}
