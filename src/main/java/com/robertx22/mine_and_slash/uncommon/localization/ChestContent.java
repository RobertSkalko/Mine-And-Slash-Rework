package com.robertx22.mine_and_slash.uncommon.localization;

import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

public class ChestContent {
    public enum chestTypeEnum implements IAutoLocName {
        aura("Augment"),
        currency("Currency"),
        gear("Gear"),
        gem("Gem"),
        harvest_blue_chest("Gear From Harvest"),
        harvest_green_chest("Currency From Harvest"),
        harvest_purple_chest("Jewel From Harvest"),
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
            return AutoLocGroup.Lootboxes;
        }

        @Override
        public String locNameLangFileGUID() {
            return SlashRef.MODID + ".chest_content." + getName();
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

    public ChestContent(String type) {
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
