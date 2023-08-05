package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

public interface IAutoLocName extends IBaseAutoLoc {

    public default String getGroupName() {
        return locNameGroup().name()
            .toUpperCase()
            .replaceAll("_", " ") + " - NAMES";
    }

    AutoLocGroup locNameGroup();

    String locNameLangFileGUID();

    default boolean shouldRegisterLangName() {
        return true;
    }

    public default String formattedLocNameLangFileGUID() {
        return getPrefix() + getFormatedForLangFile(locNameLangFileGUID());
    }

    public default String translate() {
        return CLOC.translate(this.locName());
    }

    public String locNameForLangFile();

    public default MutableComponent locName(Object[] args) {
        return new TranslatableComponent(locNameLangFileGUID(), args);
    }

    public default MutableComponent locName() {
        return CLOC.blank(getFormatedForLangFile(locNameLangFileGUID()));
    }

}
