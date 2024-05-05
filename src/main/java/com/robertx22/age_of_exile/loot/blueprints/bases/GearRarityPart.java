package com.robertx22.age_of_exile.loot.blueprints.bases;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.MapBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.Arrays;
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

    // todo clean this up
    public List<GearRarity> getPossibleRarities() {

        // todo fix this better
        if (blueprint instanceof SkillGemBlueprint) {
            return ExileDB.GearRarities().getFiltered(x -> this.blueprint.info.level >= x.min_lvl && !x.is_unique_item && x.getLowerRarity().isPresent() || x.hasHigherRarity());
        }

        if (this.blueprint instanceof MapBlueprint) {
            return Arrays.asList(ExileDB.GearRarities().get(IRarity.COMMON_ID));
        }


        var filt = ExileDB.GearRarities().getFilterWrapped(x -> this.blueprint.info.level >= x.min_lvl);

        if (!canRollUnique) {
            filt = filt.of(x -> !x.is_unique_item);
        }

        // prevent high rarity gear from dropping in low rarity maps
        filt = filt.of(x -> this.blueprint.info.map_tier >= ExileDB.GearRarities().get(x.min_map_rarity_to_drop).map_tiers.min);

        return filt.list;

    }

    @Override
    protected GearRarity generateIfNull() {

        if (this.specialRar != null) {
            return specialRar;
        }
        var possible = getPossibleRarities();
        GearRarity rar = RandomUtils.weightedRandom(possible);

        if (rar.hasHigherRarity()) {
            var higher = rar.getHigherRarity();

            // the rarer the higher rarity, the harder it should be for magic find to upgrade the rarity
            float chanceMulti = MathHelper.clamp((float) higher.Weight() / (float) rar.Weight(), 0F, 1F);

            float chance = chanceForHigherRarity * chanceMulti;

            if (RandomUtils.roll(chance)) {
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


