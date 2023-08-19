package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;

public class LootChestPart extends BlueprintPart<LootChest, ItemBlueprint> {

    public LootChestPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected LootChest generateIfNull() {
        return ExileDB.LootChests().getFilterWrapped(x -> x.getDropReq().canDropInLeague(blueprint.info.league)).random();
    }
}