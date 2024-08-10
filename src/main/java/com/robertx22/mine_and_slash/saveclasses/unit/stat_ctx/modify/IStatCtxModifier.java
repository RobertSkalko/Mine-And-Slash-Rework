package com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.modify;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;

public interface IStatCtxModifier {

    default void modify(ExactStatData thisStat, StatContext target) {
        float multi = 1F + thisStat.getValue() / 100F;
        target.multiplyStats(multi);
    }

    StatContext.StatCtxType getCtxTypeNeeded();

}
