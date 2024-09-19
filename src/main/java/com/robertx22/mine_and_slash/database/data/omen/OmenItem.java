package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;

public class OmenItem extends AutoItem {

    public OmenItem() {
        super(new Properties().stacksTo(1).durability(2500));
    }

    @Override
    public String locNameForLangFile() {
        return "Omen";
    }

    @Override
    public String GUID() {
        return "omen";
    }
}
