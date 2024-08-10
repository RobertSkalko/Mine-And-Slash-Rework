package com.robertx22.mine_and_slash.database.data.profession.stat;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

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
