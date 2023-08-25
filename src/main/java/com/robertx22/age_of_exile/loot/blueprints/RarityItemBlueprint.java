package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;

public abstract class RarityItemBlueprint extends ItemBlueprint {

    public RarityItemBlueprint(LootInfo info) {
        super(info);
    }

    public GearRarityPart rarity = new GearRarityPart(this);

}
