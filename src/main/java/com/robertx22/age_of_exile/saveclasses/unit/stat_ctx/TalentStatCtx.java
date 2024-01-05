package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.List;

public class TalentStatCtx extends StatContext {


    public TalentStatCtx(List<ExactStatData> stats) {
        super(StatCtxType.TALENT, stats);
    }
}