package com.robertx22.mine_and_slash.database.data.profession.stat;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class TripleDropChance extends Stat {
    String prof;

    public TripleDropChance(String prof) {
        this.prof = prof;
    }


    @Override
    public String locDescForLangFile() {
        return "Chance for profession to triple the drop, if it happens";
    }


    private TripleDropChance() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

    }

    @Override
    public String GUID() {
        return prof + "_triple_drop";
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
        return "Triple Drop Chance";
    }


}
