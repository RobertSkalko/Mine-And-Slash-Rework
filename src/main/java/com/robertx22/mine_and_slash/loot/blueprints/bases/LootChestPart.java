package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChest;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;

public class LootChestPart extends BlueprintPart<LootChest, ItemBlueprint> {

    public LootChestPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected LootChest generateIfNull() {
        return ExileDB.LootChests().getFilterWrapped(x -> blueprint.info.level >= x.minLevelDrop() && x.getDropReq().canDropInLeague(blueprint.info.league, blueprint.info.level)).random();
    }
}