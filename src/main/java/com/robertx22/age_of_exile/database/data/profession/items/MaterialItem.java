package com.robertx22.age_of_exile.database.data.profession.items;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.capability.player.data.IGoesToBackpack;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.temp.SkillItemTier;


public class MaterialItem extends AutoItem implements IGoesToBackpack {

    public SkillItemTier tier;
    String name;

    public MaterialItem(SkillItemTier tier, String name) {
        super(new Properties());
        this.tier = tier;
        this.name = name;
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + name;
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
