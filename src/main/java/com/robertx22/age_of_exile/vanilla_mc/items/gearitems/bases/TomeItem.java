package com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;

public class TomeItem extends AutoItem {
    public TomeItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public String locNameForLangFile() {
        return "Tome";
    }

    @Override
    public String GUID() {
        return "";
    }

    @Override
    public void generateModel(ItemModelManager manager) {

    }
}
