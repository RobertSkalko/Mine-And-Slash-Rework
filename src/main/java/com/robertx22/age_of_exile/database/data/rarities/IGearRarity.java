package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface IGearRarity extends Rarity, SalvagableItem {
  

    float valueMulti();


    float itemTierPower();

    int getAffixAmount();

    default int maximumOfOneAffixType() {
        return getAffixAmount() / 2;
    }


}
