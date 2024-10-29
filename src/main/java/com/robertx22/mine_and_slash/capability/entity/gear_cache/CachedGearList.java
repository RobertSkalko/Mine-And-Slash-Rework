package com.robertx22.mine_and_slash.capability.entity.gear_cache;

import java.util.ArrayList;
import java.util.List;

public abstract class CachedGearList {

    public List<CachedGear> all = new ArrayList<>();

    public abstract void recalc();
}
