package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.data.loot_chest.*;

public class LootChests {

    public static void init() {


        new CurrencyLootChest().registerToExileRegistry();
        new GearLootChest().registerToExileRegistry();
        new MapLootChest().registerToExileRegistry();
        new AuraLootChest().registerToExileRegistry();
        new SupportLootChest().registerToExileRegistry();
        new GemLootChest().registerToExileRegistry();
        new RuneLootChest().registerToExileRegistry();

      
    }
}
