package com.robertx22.mine_and_slash.database.data.stats.datapacks.base;

import com.robertx22.mine_and_slash.database.OptScaleExactStat;

import java.util.ArrayList;
import java.util.List;


public class CoreStatData {


    public List<OptScaleExactStat> stats = new ArrayList<>();

    public static CoreStatData of(List<OptScaleExactStat> stats) {
        CoreStatData data = new CoreStatData();
        data.stats = stats;
        return data;

    }

}
