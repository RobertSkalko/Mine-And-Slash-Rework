package com.robertx22.mine_and_slash.capability.entity;

import com.robertx22.mine_and_slash.database.data.stats.types.MaximumChargesStat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;

import java.util.HashMap;


// todo maybe instead there should be a way for stats to modify player data in a datapack way?
// like a on stat calculation event?
public class UnsavedMaxEffectStacksData {

    public HashMap<String, Integer> bonus = new HashMap<>();

    public void calc(StatContainer c) {

        for (StatData stat : c.stats.values()) {
            if (stat.GetStat() instanceof MaximumChargesStat b) {
                bonus.put(b.effect.GUID(), (int) stat.getValue());
            }
        }
    }

}
