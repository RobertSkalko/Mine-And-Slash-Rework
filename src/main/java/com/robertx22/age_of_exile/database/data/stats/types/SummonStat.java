package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public abstract class SummonStat extends Stat {


    public abstract Stat getStatToGiveToSummon();

    public abstract ModType getModType();

    public ExactStatData giveToSummon(StatData data) {

        return ExactStatData.noScaling(data.getValue(), getModType(), getStatToGiveToSummon().GUID());


    }


}
