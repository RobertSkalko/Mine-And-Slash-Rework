package com.robertx22.age_of_exile.aoe_data.database.custom_item_gens;

import com.robertx22.age_of_exile.database.data.custom_item.CustomItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class CustomItems implements ExileRegistryInit {
    @Override
    public void registerAll() {

        CustomItem test = new CustomItem();
        test.id = "test_gen";
        test.addToSerializables();
    }
}
