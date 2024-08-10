package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.GearRarityPart;

public abstract class RarityItemBlueprint extends ItemBlueprint {

    public RarityItemBlueprint(LootInfo info) {
        super(info);
    }

    public GearRarityPart rarity = new GearRarityPart(this);

}
