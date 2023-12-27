package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.List;

public interface ICoreStat extends IGUID {

    void addToOtherStats(Unit unit, InCalcStatData data);

    List<OptScaleExactStat> statsThatBenefit();

}