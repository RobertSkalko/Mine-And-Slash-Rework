package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class UberFragmentItem extends AutoItem {

    public int uberTier;
    public UberEnum uber;

    public UberFragmentItem(int uberTier, UberEnum uber) {
        super(new Properties().stacksTo(1));
        this.uberTier = uberTier;
        this.uber = uber;
    }

    @Override
    public String locNameForLangFile() {
        return uber.fragmentName;
    }

    @Override
    public String GUID() {
        return null;
    }
}
