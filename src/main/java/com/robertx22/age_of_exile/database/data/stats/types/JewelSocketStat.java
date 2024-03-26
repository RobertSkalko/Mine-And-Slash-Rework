package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class JewelSocketStat extends Stat {

    private JewelSocketStat() {
        this.max = 9;
    }

    public static JewelSocketStat getInstance() {
        return JewelSocketStat.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.NONE;
    }

    @Override
    public String locDescForLangFile() {
        return "Allows you to socket more jewels";
    }

    @Override
    public String GUID() {
        return "jewel_socket";
    }

    @Override
    public String locNameForLangFile() {
        return "Jewel Sockets";
    }

    private static class SingletonHolder {
        private static final JewelSocketStat INSTANCE = new JewelSocketStat();
    }
}
