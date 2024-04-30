package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.saveclasses.unit.StatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.library_of_exile.registry.IGUID;

public interface AddToAfterCalcEnd extends IGUID {
    void affectStats(StatContainer copy, StatContainer stats, StatData statData);

}
