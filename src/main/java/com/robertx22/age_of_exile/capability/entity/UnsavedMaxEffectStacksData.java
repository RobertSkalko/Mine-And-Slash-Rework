package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.database.data.stats.types.BonusChargesStat;
import com.robertx22.age_of_exile.saveclasses.unit.StatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

import java.util.HashMap;


// todo maybe instead there should be a way for stats to modify player data in a datapack way?
// like a on stat calculation event?
public class UnsavedMaxEffectStacksData {

    public HashMap<String, Integer> bonus = new HashMap<>();

    public void calc(StatContainer c) {

        for (StatData stat : c.stats.values()) {
            if (stat.GetStat() instanceof BonusChargesStat b) {
                bonus.put(b.effect.GUID(), (int) stat.getValue());
            }
        }
    }

}
