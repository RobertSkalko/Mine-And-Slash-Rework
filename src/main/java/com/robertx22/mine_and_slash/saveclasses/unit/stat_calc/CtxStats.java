package com.robertx22.mine_and_slash.saveclasses.unit.stat_calc;

import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CtxStats {

    public HashMap<StatContext.StatCtxType, List<StatContext>> map = new HashMap<>();

    public CtxStats(List<StatContext> list) {
        for (StatContext.StatCtxType type : StatContext.StatCtxType.values()) {
            map.put(type, new ArrayList<>());
        }
        // create map
        list.forEach(x -> {
            map.get(x.type).add(x);
        });
    }

    public static StatContext addStatCtxModifierStats(List<StatContext> list) {
        CtxStats c = new CtxStats(list);

        return c.addCtxModifierStats();
    }

    public CtxStats() {
    }

    public void applyToInCalc(InCalcStatContainer unit) {
        map.forEach((key, value) -> value
                .forEach(v -> {
                    v.stats.forEach(s -> {
                        s.applyToStatInCalc(unit);
                    });
                }));
    }

    public StatContext addCtxModifierStats() {
        List<ExactStatData> list = new ArrayList<>();
        // apply ctx modifier stats
        map.forEach((key, value) -> value
                .forEach(v -> {
                    v.stats.forEach(s -> {
                        if (s.getStat() == null) {
                            //ExileLog.get().log(s.getStatId());
                        } else {
                            if (s.getStat().statContextModifier != null) {
                                map.get(s.getStat().statContextModifier.getCtxTypeNeeded()).forEach(c -> {
                                    list.addAll(s.getStat().statContextModifier.modify(s, c));
                                });
                            }
                        }
                    });
                }));

        return new SimpleStatCtx(StatContext.StatCtxType.STAT_CTX_MODIFIER_BONUS, list);
    }
}
