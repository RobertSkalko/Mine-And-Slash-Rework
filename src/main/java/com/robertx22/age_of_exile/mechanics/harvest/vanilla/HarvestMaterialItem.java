package com.robertx22.age_of_exile.mechanics.harvest.vanilla;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class HarvestMaterialItem extends AutoItem {

    String name;

    public HarvestMaterialItem(String name) {
        super(new Properties().stacksTo(64));
        this.name = name;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return "";
    }
}
