package com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base;

import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;

import java.util.List;

public interface ICoreStat extends IGUID {

    //   void addToOtherStats(EntityData endata, InCalcStatContainer unit, InCalcStatData data);

    List<OptScaleExactStat> statsThatBenefit();

    void affectStats(EntityData endata, StatData data, InCalcStatContainer incalc);

}