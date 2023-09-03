package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.temp.SkillItemTier;


// todo will items have all variations or get their variation via nbt
public class MaterialItem extends AutoItem {

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
}
