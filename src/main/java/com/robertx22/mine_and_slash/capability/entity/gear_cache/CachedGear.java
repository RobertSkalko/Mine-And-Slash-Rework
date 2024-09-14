package com.robertx22.mine_and_slash.capability.entity.gear_cache;

import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;

import java.util.List;

public class CachedGear {

    public List<StatContext> stats;

    public CachedGear(List<StatContext> stats) {
        this.stats = stats;
    }
}
