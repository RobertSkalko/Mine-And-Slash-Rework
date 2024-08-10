package com.robertx22.mine_and_slash.database.data.stats.types.gear_base;

import com.robertx22.mine_and_slash.database.data.stats.Stat;

public interface IBaseStatModifier {

    public boolean canModifyBaseStat(Stat stat);
}
