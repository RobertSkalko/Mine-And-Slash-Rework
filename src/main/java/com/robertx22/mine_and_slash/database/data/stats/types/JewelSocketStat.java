package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

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
        return Elements.ALL;
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
