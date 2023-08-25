package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class LeechCapStat extends Stat {
    public static LeechCapStat getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final LeechCapStat INSTANCE = new LeechCapStat();
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Your leech/resource on hits are capped to x% per second, this stat increases that.";
    }

    @Override
    public String locNameForLangFile() {
        return "Total Leech Per Second";
    }

    @Override
    public String GUID() {
        return "leech_per_sec";
    }
}
