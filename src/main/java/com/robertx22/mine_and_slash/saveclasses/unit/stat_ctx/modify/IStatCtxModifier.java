package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.modify;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;

import java.util.List;

public interface IStatCtxModifier {

    default List<ExactStatData> modify(ExactStatData thisStat, StatContext target) {
        float multi = thisStat.getValue() / 100F;
        return target.getPercentOfStats(multi);
    }

    StatContext.StatCtxType getCtxTypeNeeded();

}
