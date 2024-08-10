package com.robertx22.mine_and_slash.aoe_data.database.auto_items;

import com.robertx22.mine_and_slash.database.data.auto_item.AutoItem;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;

public class AutoItems implements ExileRegistryInit {
    @Override
    public void registerAll() {
        AutoItem.of("test_auto", VanillaUTIL.REGISTRY.items().getKey(SlashItems.TEST_GEN.get()).toString(), "test_gen");
    }
}
