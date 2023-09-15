package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.capability.player.data.IGoesToBackpack;
import com.robertx22.age_of_exile.database.data.profession.CraftedItemPower;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class RareMaterialItem extends AutoItem implements IGoesToBackpack {

    public CraftedItemPower tier;
    String name;
    public int weight;

    public RareMaterialItem(CraftedItemPower tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
        this.weight = tier.weight;
    }

    @Override
    public String locNameForLangFile() {
        return tier.word.locNameForLangFile() + " " + name;
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public Backpacks.BackpackType getBackpackPickup() {
        return Backpacks.BackpackType.PROFESSION;
    }
}