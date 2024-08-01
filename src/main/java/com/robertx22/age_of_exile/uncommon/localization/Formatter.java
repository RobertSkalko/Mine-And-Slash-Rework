package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Formatter implements IAutoLocName {

    UNIQUE_NAME_FORMAT("%1$s %2$s"),
    GEM_ITEM_NAME("%1$s %2$s", "Use to control the order and space of gem item name, like \"(cracked) (ruby)\""),
    BUFF_CONSUMPTIONS_NAME("%1$s %2$s %3$s", "control the order and space of buff consumption."),
    GEAR_ITEM_NAME_ALL("%1$s %2$s %3$s", "Use to control the order and space of gear item name, like \"(Giant) (Rusted Sword) (of Penetration)\""),
    ICON_AND_DAMAGE_IN_SPELL_DAMAGE_PROPORTION("%1$s %2$s", "this is aim to control the order and space of spell damage calc detail, the whole calc format, like \"25% of (a icon) Weapon damage\", is controlled by spell_damage_proportion key, this only affect the part \"(a icon) Weapon damage\""),
    SPECIAL_CALC_STAT("%1$s %2$s", "it will only work when stat has a prefix(\"increased\", \"Reduced\", \"More\", \"Less\")"),
    SECOND_SPECIAL_CALC_STAT("%1$s %2$s"),
    GEM_CHEST_NAME("%1$s %2$s %3$s %4$s"),
    SPECIAL_UNIQUE_PROCESS("%1$s %2$s", "currently no use"),
    NORMAL_CALC_STAT("%1$s", "currently no use"),
    GEAR_ITEM_NAME_ONLY_GEAR("%1$s"),
    GEAR_ITEM_NAME_PRE_GEAR("%1$s %2$s"),
    GEAR_ITEM_NAME_ANOTHER("%1$s %2$s %3$s");
    private String localization = "";

    private String localeContext = null;

    Formatter(String str) {
        this.localization = str;

    }
    Formatter(String str, String localeContext) {
        this.localization = str;
        this.localeContext = localeContext;

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

    @Override
    public String additionLocInformation(){
        return localeContext;
    }

}
