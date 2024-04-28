package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;

public enum UberEnum {
    WITHER("uber1", "Realm of Atrophy and Decay", "Withering Fragment"),
    UNUSED2("uber2", "unamed", "unamed"),
    UNUSED3("uber3", "unamed", "unamed"),
    UNUSED4("uber4", "unamed", "unamed"),
    UNUSED5("uber5", "unamed", "unamed"),
    UNUSED6("uber6", "unamed", "unamed"),
    UNUSED7("uber7", "unamed", "unamed"),
    UNUSED8("uber8", "unamed", "unamed"),
    UNUSED9("uber9", "unamed", "unamed");

    public String id;
    public String arenaName;
    public String fragmentName;

    UberEnum(String id, String arenaName, String fragmentName) {
        this.id = id;
        this.arenaName = arenaName;
        this.fragmentName = fragmentName;
    }

    public UberFragmentItem getFragment(int tier) {
        return SlashItems.UBER_FRAGS.get(this).get(tier).get();
    }

    public UberBossMapItem getMap(int tier) {
        return SlashItems.UBER_MAPS.get(this).get(tier).get();
    }
}
