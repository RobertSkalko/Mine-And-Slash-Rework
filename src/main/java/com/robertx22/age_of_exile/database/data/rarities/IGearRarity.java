package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public interface IGearRarity extends Rarity, SalvagableItem {
    @Override
    public default ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_RARITY;
    }


    float valueMulti();


    float itemTierPower();

    int getAffixAmount();

    default int maximumOfOneAffixType() {
        return getAffixAmount() / 2;
    }


}
