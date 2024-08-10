package com.robertx22.mine_and_slash.vanilla_mc.items;

import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;

public class SourceOfStrengthItem extends AutoItem {
    public SourceOfStrengthItem() {
        super(new Properties());
    }

    @Override
    public String locNameForLangFile() {
        return "Source of Strength";
    }

    @Override
    public String GUID() {
        return "source_of_strength";
    }
}
