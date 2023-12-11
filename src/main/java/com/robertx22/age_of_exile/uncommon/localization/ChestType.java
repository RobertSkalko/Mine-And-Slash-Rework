package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public class ChestType {
    public enum chestTypeEnum implements IAutoLocName {
        aura("Aura"),
        currency("Currency"),
        gear("Gear"),
        gem("Gem"),
        harvest_blue_chest("Harvest Blue Chest"),
        harvest_green_chest("Harvest Green Chest"),
        harvest_purple_chest("Harvest Purple Chest"),
        map("Map"),
        rune("Rune"),
        support_gem("Support Gem");

        private final String localization;

        chestTypeEnum(String localization) {
            this.localization = localization;
        }
        public String getName() {
            return this.name();
        }
        public String getValue() {
            return localization;
        }

        @Override
        public AutoLocGroup locNameGroup() {
            return AutoLocGroup.Misc;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".chest_type." + getName();
        }

        @Override
        public String locNameForLangFile() {
            return localization;
        }

        @Override
        public String GUID() {
            return getName();
        }
    }

    private final String chestType;

    public ChestType(String type) {
        this.chestType = type;
    }

    public chestTypeEnum get() {
        for (chestTypeEnum type : chestTypeEnum.values()) {
            if (type.getName().equalsIgnoreCase(chestType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid input string: " + chestType);
    }


}
