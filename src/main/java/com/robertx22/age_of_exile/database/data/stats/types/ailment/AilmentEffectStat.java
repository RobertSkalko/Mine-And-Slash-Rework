package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AilmentEffectStat extends Stat {

    Ailment ailment;

    public AilmentEffectStat(Ailment ailment) {
        this.ailment = ailment;
    }

    @Override
    public Elements getElement() {
        return ailment.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases the effect, if it's a DOT, increases damage, if it's not, decreases the amount of damage needed to fully apply it.";
    }

    @Override
    public String locNameForLangFile() {
        return ailment.locNameForLangFile() + " Strength";
    }

    @Override
    public String GUID() {
        return ailment.GUID() + "_strength";
    }
}
