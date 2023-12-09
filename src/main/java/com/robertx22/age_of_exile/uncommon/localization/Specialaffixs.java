package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum Specialaffixs implements IAutoLocName {

    Miracle("Miracle"),
    Oblivion("Oblivion"),
    Beast("Beast"),
    Golem("Golem"),
    Spirit("Spirit"),
    Rage("Rage"),

    Beads("Beads"),
    Charm("Charm"),
    Locket("Locket"),
    //
    Band("Band"),
    Eye("Eye"),
    Loop("Loop"),
    //
    Crown("Crown"),
    Circlet("Circlet"),
    Horn("Horn"),
    //
    Cloak("Cloak"),
    Coat("Coat"),
    Mantle("Mantle"),
    Shell("Shell"),
    //
    Aegis("Aegis"),
    Barrier("Barrier"),
    Guard("Guard"),
    Tower("Tower"),
    Road("Road"),
    Hoof("Hoof"),
    Dash("Dash"),
    //

    Bane("Bane"),
    Bite("Bite"),
    Wind("Wind"),
    Star("Star"),
    Splitter("Splitter"),
    //
    Legguards("Legguards"),
    Leggings("Leggings"),
    Britches("Britches"),
    Legwraps("Legwraps"),
    //
    Creation("Creation"),
    Crest("Crest"),
    Keep("Keep"),
    Ward("Ward"),
    Refuge("Refuge");


    private String localization = "";

    Specialaffixs(String str) {
        this.localization = str;

    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Words;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".special_affixs." + GUID();
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
