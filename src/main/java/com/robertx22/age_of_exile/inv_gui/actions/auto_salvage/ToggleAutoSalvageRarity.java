package com.robertx22.age_of_exile.inv_gui.actions.auto_salvage;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;

public class ToggleAutoSalvageRarity {

    public SalvageType type;
    public GearRarity rarity;

    public enum SalvageType {

        GEAR,
        SPELL,

    }


}
