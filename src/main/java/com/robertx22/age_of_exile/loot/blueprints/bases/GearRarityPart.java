package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.List;

public class GearRarityPart extends BlueprintPart<GearRarity, ItemBlueprint> {


    GearRarity specialRar = null;

    public List<GearRarity> possible = ExileDB.GearRarities().getFiltered(x -> !x.is_unique_item);

    public float chanceForHigherRarity = 0;

    public GearRarityPart(ItemBlueprint blueprint) {
        super(blueprint);
    }

    public void setupChances(LootInfo info) {
        if (info.playerData != null) {
            // if (info.lootOrigin == LootInfo.LootOrigin.CHEST) {
            chanceForHigherRarity += info.playerData.getUnit().getCalculatedStat(TreasureQuality.getInstance()).getValue();
            //}
        }


    }

    @Override
    protected GearRarity generateIfNull() {

        if (this.specialRar != null) {
            return specialRar;
        }

        GearRarity rar = RandomUtils.weightedRandom(possible);

        if (rar.hasHigherRarity()) {
            if (RandomUtils.roll(chanceForHigherRarity)) {
                if (rar.hasHigherRarity() && possible.contains(rar.getHigherRarity())) {
                    rar = rar.getHigherRarity();
                }
            }
        }

        return rar;
    }

}


