package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.network.chat.MutableComponent;

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

    public default MutableComponent locName(Object... arg) {
        return ExileText.ofTranslate(locNameLangFileGUID(), arg).get();
    }

    public default MutableComponent locName() {
        return CLOC.blank(getFormatedForLangFile(locNameLangFileGUID()));
    }

    default String additionLocInformation(){
        return null;
    }

}
