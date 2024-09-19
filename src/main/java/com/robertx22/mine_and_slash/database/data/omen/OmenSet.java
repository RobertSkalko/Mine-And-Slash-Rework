package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OmenSet {


    public HashMap<Integer, List<ExactStatData>> stats = new HashMap<>();

    public OmenSet(OmenData data) {

        int num = 2;

        int max = (int) data.rarities.values().stream().count();

        int index = 0;

        var mods = data.getOmen().mods;

        for (int i = num; i < max; i++) {

            List<ExactStatData> exacts = new ArrayList<>();

            if (data.aff.size() > index) {
                var affixdata = data.aff.get(index);
                exacts.addAll(affixdata.GetAllStats(data.lvl));
            } else {
                int perc = data.getStatPercent(data.rarities, data.slot_req, data.getRarity());
                for (StatMod mod : mods) {
                    exacts.add(mod.ToExactStat(perc, data.lvl));
                }
            }

            stats.put(i, exacts);

            index++;
        }

    }
}
