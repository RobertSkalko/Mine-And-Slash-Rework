package com.robertx22.mine_and_slash.database.data.loot_chest.base;

import com.robertx22.mine_and_slash.database.data.loot_chest.*;

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
