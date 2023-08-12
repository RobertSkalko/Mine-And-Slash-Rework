package com.robertx22.age_of_exile.database.data.stats.types.gear_base;

import com.robertx22.age_of_exile.database.data.stats.Stat;

public interface IBaseStatModifier {

    public boolean canModifyBaseStat(Stat stat);
}
