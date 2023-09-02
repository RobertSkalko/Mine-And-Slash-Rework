package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.List;

public class GearRarityPart extends BlueprintPart<GearRarity, ItemBlueprint> {


    GearRarity specialRar = null;


    public float chanceForHigherRarity = 0;

    public boolean canRollUnique = false;

    public GearRarityPart(ItemBlueprint blueprint) {
        super(blueprint);
        if (blueprint instanceof GearBlueprint) {
            canRollUnique = true;
        }
    }

    public void setupChances(LootInfo info) {
        if (info.playerEntityData != null) {
            chanceForHigherRarity += info.playerEntityData.getUnit().getCalculatedStat(TreasureQuality.getInstance()).getValue();
        }
    }

    public List<GearRarity> getPossibleRarities() {
        if (canRollUnique) {
            return ExileDB.GearRarities().getFiltered(x -> this.blueprint.info.level >= x.min_lvl);
        }
        return ExileDB.GearRarities().getFiltered(x -> this.blueprint.info.level >= x.min_lvl && !x.is_unique_item);
    }

    @Override
    protected GearRarity generateIfNull() {

        if (this.specialRar != null) {
            return specialRar;
        }

        GearRarity rar = RandomUtils.weightedRandom(getPossibleRarities());

        if (rar.hasHigherRarity()) {
            if (RandomUtils.roll(chanceForHigherRarity)) {
                if (rar.hasHigherRarity() && getPossibleRarities().contains(rar.getHigherRarity())) {
                    if (blueprint.info.level >= rar.getHigherRarity().min_lvl) {
                        rar = rar.getHigherRarity();
                    }
                }
            }
        }

        return rar;
    }

}


