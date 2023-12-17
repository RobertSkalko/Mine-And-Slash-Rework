package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

public enum EffectTags implements IAutoLocName {
    immobilizing("Immobilizing"),
    offensive("Offensive"),
    curse("Curse"),
    food("Food"),
    defensive("Defensive"),
    positive("Positive"),
    song("Song"),
    negative("Negative"),
    heal_over_time("Heal over Time");

    String tag;

    EffectTags(String name) {
        this.tag = name;
    }


    public String getLocName() {
        return tag;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".effect_tag." + this.name();
    }

    @Override
    public String locNameForLangFile() {
        return this.tag;
    }

    @Override
    public String GUID() {
        return this.name();
    }
}
