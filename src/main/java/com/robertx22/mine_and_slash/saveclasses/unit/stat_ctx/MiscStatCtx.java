package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;

import java.util.List;

public class MiscStatCtx extends StatContext {

    public MiscStatCtx(List<ExactStatData> stats) {
        super(StatCtxType.MISC, stats);
    }
}
