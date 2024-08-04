package com.robertx22.age_of_exile.database.documentation.test;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.documentation.*;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;

import java.util.HashMap;
import java.util.List;

public class UniqueGearWrapper extends JsonWrapper<UniqueGear> {

    private UniqueGear uniq;

    public UniqueGearWrapper(UniqueGear x) {
        this.uniq = x;

        id = of("guid", new JsonIdentifierField(x.guid));

        uniqueStats = of("unique_stats", new GenericField(x.unique_stats, "The stats of the unique. These stats are always the same for the unique, they only vary in numbers."));

        baseGear = of("base_gear", new DatabaseField(x.base_gear, ExileRegistryTypes.GEAR_TYPE));
        rarity = of("rarity", new DatabaseField(x.rarity, ExileRegistryTypes.GEAR_RARITY));
        league = of("league", new DatabaseField(x.league, ExileRegistryTypes.LEAGUE_MECHANIC));

        forceItem = of("force_item_id", new ItemField(x.force_item_id));
        weight = of("weight", new WeightField(x.weight));

        minTier = of("min_tier", new IntegerField(x.min_tier, "The minimum map tier this unique can drop from."));

        canBeRuned = of("runable", new BooleanField(x.runable, "Whether the Unique can have runes socketed on it."));
        replacesName = of("replaces_name", new BooleanField(x.replaces_name, "If name is replaced, it will only use unique's lang entry, if it's not replaced, it will add the base gear type to the name."));

        league.setCanBeEmpty();

        testFieldsExist();
    }


    public DatabaseField<BaseGearType> baseGear;
    public DatabaseField<GearRarity> rarity;
    public DatabaseField<LeagueMechanic> league;
    public BooleanField canBeRuned;
    public BooleanField replacesName;
    public ItemField forceItem;
    public JsonIdentifierField id;
    public WeightField weight;
    public IntegerField minTier;
    public GenericField<List<StatMod>> uniqueStats;


    @Override
    public HashMap<String, JsonField> getFieldMap() {
        return fieldMap;
    }

    @Override
    public UniqueGear getWrapped() {
        return uniq;
    }
}
