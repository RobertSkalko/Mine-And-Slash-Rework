package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.mmorpg.ModErrors;

public class UniqueGearPart extends BlueprintPart<UniqueGear, GearBlueprint> {

    public UniqueGearPart(GearBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected UniqueGear generateIfNull() {
        try {
            var filt = ExileDB.UniqueGears()
                    .getWrapped()
                    .of(x -> x.rarity.equals(blueprint.rarity.get().GUID()))
                    .of(x -> blueprint.info.map_tier >= x.min_tier)
                    .of(x -> x.canSpawnInLeague(blueprint.info.league))
                    .of(x -> x.getBaseGear().GUID().equals(blueprint.gearItemSlot.get().GUID()));

            if (!filt.list.isEmpty()) {
                var uniq = filt.random();
                blueprint.gearItemSlot.override(uniq.getBaseGear());
                return uniq;
            } else {
                UniqueGear uniq = ExileDB.UniqueGears().getFilterWrapped(x -> blueprint.info.map_tier >= x.min_tier).random();
                if (uniq != null) {
                    blueprint.gearItemSlot.override(uniq.getBaseGear());
                }
                return uniq;
            }


        } catch (Exception e) {
            ModErrors.print(e);

            UniqueGear uniq = ExileDB.UniqueGears().random();
            if (uniq != null) {
                blueprint.gearItemSlot.override(uniq.getBaseGear());
            }
            return uniq;
        }
    }

}
