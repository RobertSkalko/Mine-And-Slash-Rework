package com.robertx22.age_of_exile.database.data.stats.datapacks.base;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CoreStatData {


    public List<Data> stats = new ArrayList<>();

    public static CoreStatData of(List<OptScaleExactStat> stats) {
        CoreStatData data = new CoreStatData();
        data.stats = stats.stream()
                .map(x -> new Data(x.v1, x.stat, x.type))
                .collect(Collectors.toList());
        return data;

    }


    public static class Data {

        public float v1 = 0;

        public String stat;

        public String type;

        public Data(float v1, String stat, String type) {
            this.v1 = v1;
            this.stat = stat;
            this.type = type;
        }

        public OptScaleExactStat getStat() {
            return new OptScaleExactStat(v1, ExileDB.Stats()
                    .get(stat), ModType.fromString(type));
        }
    }
}
