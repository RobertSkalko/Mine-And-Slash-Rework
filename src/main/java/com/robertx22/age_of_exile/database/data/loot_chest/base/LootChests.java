package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.data.loot_chest.CurrencyLootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.GearLootChest;
import com.robertx22.age_of_exile.mechanics.harvest.loot.HarvestBlueChest;
import com.robertx22.age_of_exile.mechanics.harvest.loot.HarvestGreenChest;
import com.robertx22.age_of_exile.mechanics.harvest.loot.HarvestPurpleChest;

public class LootChests {

    public static void init() {

        new CurrencyLootChest().registerToExileRegistry();
        new GearLootChest().registerToExileRegistry();

        new HarvestPurpleChest().registerToExileRegistry();
        new HarvestBlueChest().registerToExileRegistry();
        new HarvestGreenChest().registerToExileRegistry();
        
    }
}
