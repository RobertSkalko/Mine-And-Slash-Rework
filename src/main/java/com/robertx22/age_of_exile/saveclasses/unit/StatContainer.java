package com.robertx22.age_of_exile.saveclasses.unit;

import java.util.HashMap;
import java.util.Map;


public class StatContainer {


    public HashMap<String, StatData> stats = new HashMap<>();

    public StatData getCalculatedStat(String guid) {

        return stats.getOrDefault(guid, StatData.empty());
    }

    public StatData getOrCreateCalculatedStat(String guid) {
        var data = stats.getOrDefault(guid, new StatData(guid, 0, 1));
        stats.put(guid, data);
        return data;
    }

    public StatContainer() {
    }

    public StatContainer clone() {
        StatContainer c = new StatContainer();

        for (Map.Entry<String, StatData> en : stats.entrySet()) {
            var stat = en.getValue();
            c.stats.put(en.getKey(), new StatData(stat.getId(), stat.getValue(), stat.getMoreStatTypeMulti()));
        }

        return c;

    }

}
