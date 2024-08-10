package com.robertx22.mine_and_slash.gui.stats;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;

import java.util.ArrayList;
import java.util.List;

public class SavedStatCtx {


    public List<ExactStatData> stats = new ArrayList<>();

    public StatContext.StatCtxType type = StatContext.StatCtxType.MISC;

    public SavedStatCtx() {
    }

    public SavedStatCtx(List<ExactStatData> stats, StatContext.StatCtxType type) {
        this.stats = stats;
        this.type = type;
    }
}
