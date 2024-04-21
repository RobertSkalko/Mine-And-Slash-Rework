package com.robertx22.age_of_exile.gui.stats;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_calc.CtxStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavedStatCtxList {

    public List<SavedStatCtx> list = new ArrayList<>();

    public SavedStatCtxList(CtxStats sc) {

        for (Map.Entry<StatContext.StatCtxType, List<StatContext>> en : sc.map.entrySet()) {
            List<ExactStatData> stats = new ArrayList<>();

            for (StatContext stat : en.getValue()) {
                stats.addAll(stat.stats);
            }
            list.add(new SavedStatCtx(stats, en.getKey()));
        }

    }

    public SavedStatCtxList() {
    }
}
