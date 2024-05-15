package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public enum StatGuiGroup implements IAutoLocName {

    NONE("none", "None"),
    RESIST("resist", "Elemental Resists"),
    ELE_DAMAGE("ele_damage", "Elemental Damage");

    public String id;
    public String name;

    StatGuiGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean isValid() {
        return this != NONE;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatGroup;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".stat_group." + id;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return id;
    }
}
