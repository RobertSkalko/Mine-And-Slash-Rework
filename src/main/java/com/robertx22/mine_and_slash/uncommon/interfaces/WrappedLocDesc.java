package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;

public class WrappedLocDesc implements IAutoLocDesc {
    AutoLocGroup group;
    String text;
    ExileRegistry type;

    public WrappedLocDesc(ExileRegistry type, AutoLocGroup group, String text) {
        this.group = group;
        this.text = text;
        this.type = type;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return group;
    }

    @Override
    public String locDescLangFileGUID() {
        // todo replaceall tweak from api
        return SlashRef.MODID + "." + type.getExileRegistryType().id.replaceAll("mmorpg_", "") + ".desc." + GUID();
    }


    @Override
    public String locDescForLangFile() {
        return text;
    }

    @Override
    public String GUID() {
        return type.GUID();
    }
}
