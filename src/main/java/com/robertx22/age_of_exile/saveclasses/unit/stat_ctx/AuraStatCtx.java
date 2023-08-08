package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.List;

public class AuraStatCtx extends StatContext {


    public AuraStatCtx(List<ExactStatData> stats) {
        super(StatCtxType.AURA, stats);
    }
}
