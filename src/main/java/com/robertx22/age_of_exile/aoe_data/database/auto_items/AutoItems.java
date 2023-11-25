package com.robertx22.age_of_exile.aoe_data.database.auto_items;

import com.robertx22.age_of_exile.database.data.auto_item.AutoItem;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;

public class AutoItems implements ExileRegistryInit {
    @Override
    public void registerAll() {
        AutoItem.of("test_auto", VanillaUTIL.REGISTRY.items().getKey(SlashItems.TEST_GEN.get()).toString(), "test_gen");
    }
}
