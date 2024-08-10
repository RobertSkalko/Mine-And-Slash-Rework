package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public abstract class SummonStat extends Stat {


    public abstract Stat getStatToGiveToSummon();

    public abstract ModType getModType();

    public ExactStatData giveToSummon(StatData data) {

        return ExactStatData.noScaling(data.getValue(), getModType(), getStatToGiveToSummon().GUID());


    }


}
