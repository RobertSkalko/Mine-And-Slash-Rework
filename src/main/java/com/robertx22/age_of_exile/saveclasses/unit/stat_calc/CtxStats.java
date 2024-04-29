package com.robertx22.age_of_exile.saveclasses.unit.stat_calc;

import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;

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

    public void applyCtxModifierStats() {
        // apply ctx modifier stats
        map.forEach((key, value) -> value
                .forEach(v -> {
                    v.stats.forEach(s -> {
                        if (s.getStat() == null) {
                            //System.out.println(s.getStatId());
                        } else {
                            if (s.getStat().statContextModifier != null) {
                                map.get(s.getStat().statContextModifier.getCtxTypeNeeded()).forEach(c -> s.getStat().statContextModifier.modify(s, c));
                            }
                        }
                    });
                }));
    }
}
