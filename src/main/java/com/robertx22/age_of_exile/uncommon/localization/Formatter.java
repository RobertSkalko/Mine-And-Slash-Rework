package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Formatter implements IAutoLocName {
    GEM_ITEM_NAME("%1$s %2$s"),
    BUFF_COMSUPTIONS_NAME("%1$s %2$s %3$s"),
    GEAR_ITEM_NAME("%1$s %2$s %3$s"),
    HIGH_RARITY_GEAR_ITEM_NAME("%1$s %2$s %3$s"),
    ICON_AND_DAMAGE_IN_SPELL_DAMAGE_PROPORTION("%1$s %2$s"), SPECIAL_CALC_STAT("%1$s %2$s"), SECOND_SPECIAL_CALC_STAT("%1$s %2$s");
    private String localization = "";

    Formatter(String str) {
        this.localization = str;

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Formatter;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".formatter." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String GUID() {
        return this.name()
                .toLowerCase(Locale.ROOT);
    }

}
