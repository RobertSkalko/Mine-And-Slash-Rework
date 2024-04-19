package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.library_of_exile.registry.IGUID;

public interface AddToAfterCalcEnd extends IGUID {
    void affectStats(Unit data, StatData statData);

}
