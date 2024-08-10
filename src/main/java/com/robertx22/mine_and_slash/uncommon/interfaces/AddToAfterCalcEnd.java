package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.saveclasses.unit.StatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.library_of_exile.registry.IGUID;

public interface AddToAfterCalcEnd extends IGUID {
    void affectStats(StatContainer copy, StatContainer stats, StatData statData);

}
