package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.library_of_exile.registry.IWeighted;

public class EssenceItem extends AutoItem implements IWeighted {
    String name;
    public int weight = 1000;

    public EssenceItem(String name) {
        super(new Properties());
        this.name = name;
    }

    public EssenceItem(String name, boolean rare) {
        super(new Properties());
        this.name = name;

        if (rare) {
            weight = 50;
        }
    }

    @Override
    public String locNameForLangFile() {
        return "Essence of " + name;
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public int Weight() {
        return weight;
    }
}
