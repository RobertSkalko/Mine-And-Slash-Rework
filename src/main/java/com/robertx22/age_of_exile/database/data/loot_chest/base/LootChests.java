package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.data.loot_chest.CurrencyLootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.GearLootChest;

public class LootChests {

    public static void init() {

        new CurrencyLootChest().registerToExileRegistry();
        new GearLootChest().registerToExileRegistry();

    }
}
