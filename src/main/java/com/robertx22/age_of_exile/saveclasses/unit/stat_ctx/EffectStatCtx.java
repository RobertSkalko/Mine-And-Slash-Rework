package com.robertx22.age_of_exile.saveclasses.unit.stat_ctx;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.List;

public class EffectStatCtx extends StatContext {
    public EffectStatCtx(List<ExactStatData> stats) {
        super(StatCtxType.POTION_EFFECT, stats);
    }
}
