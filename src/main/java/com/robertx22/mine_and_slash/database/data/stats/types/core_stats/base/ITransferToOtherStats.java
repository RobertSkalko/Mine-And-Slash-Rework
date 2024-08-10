package com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base;

import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatData;
import com.robertx22.library_of_exile.registry.IGUID;

public interface ITransferToOtherStats extends IGUID {

    void transferStats(InCalcStatContainer unit, InCalcStatData thisstat);

}