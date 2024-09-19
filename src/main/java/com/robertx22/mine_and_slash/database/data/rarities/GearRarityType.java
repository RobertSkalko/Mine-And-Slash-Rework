package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.uncommon.localization.Words;

public enum GearRarityType {
    NORMAL("normal", Words.NORMAL_RARITY),
    UNIQUE("unique", Words.Unique_Gear),
    RUNED("runed", Words.Runed_gear);

    public String id;
    public Words word;

    GearRarityType(String id, Words word) {
        this.id = id;
        this.word = word;
    }
}
