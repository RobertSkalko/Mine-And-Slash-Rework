package com.robertx22.age_of_exile.database.data.profession.stat;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ProfExp extends Stat {

    String prof;

    public ProfExp(String prof) {
        this.prof = prof;
    }

    @Override
    public String locDescForLangFile() {
        return "Bonus Profession Exp";
    }

    private ProfExp() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

    }

    @Override
    public String GUID() {
        return prof + "_exp_bonus";
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
        return "Profession Exp Bonus";
    }

}
