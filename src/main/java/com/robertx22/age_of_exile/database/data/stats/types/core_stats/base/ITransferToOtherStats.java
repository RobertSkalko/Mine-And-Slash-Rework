package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.library_of_exile.registry.IGUID;

public interface ITransferToOtherStats extends IGUID {

    void transferStats(Unit unit, InCalcStatData thisstat);

}