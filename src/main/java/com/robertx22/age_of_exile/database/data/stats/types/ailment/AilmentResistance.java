package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AilmentResistance extends Stat {

    Ailment ailment;

    public AilmentResistance(Ailment ailment) {
        this.ailment = ailment;
        this.is_perc = true;

    }

    @Override
    public Elements getElement() {
        return ailment.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Protects against the ailment, if it's 100%, it acts as immunity.";
    }

    @Override
    public String locNameForLangFile() {
        return ailment.locNameForLangFile() + " Resistance";
    }

    @Override
    public String GUID() {
        return ailment.GUID() + "_resistance";
    }
}
