package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public interface IAutoLocDesc extends IBaseAutoLoc {

    public default String getDescGroupName() {
        return locDescGroup().name()
                .toUpperCase()
                .replaceAll("_", " ") + " - DESCRIPTIONS";
    }

    public AutoLocGroup locDescGroup();

    String locDescLangFileGUID();

    String locDescForLangFile();

    default boolean shouldRegisterLangDesc() {
        return true;
    }

    public default MutableComponent locDesc() {
        return CLOC.blank(formattedLocDescLangFileGUID());
    }

    public default String formattedLocDescLangFileGUID() {
        return getPrefix() + getFormatedForLangFile(locDescLangFileGUID());
    }

    public default MutableComponent getDescParams(Object... obj) {
        return Component.translatable(this.formattedLocDescLangFileGUID(), obj);
    }

}

