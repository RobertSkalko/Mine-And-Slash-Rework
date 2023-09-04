package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum CraftedItemPower {

    LESSER(1, 0, "lesser", Words.LESSER),
    MEDIUM(3, 50, "medium", Words.MEDIUM),
    GREATER(5, 100, "greater", Words.GREATER);

    public int matItems;
    public int perc;
    public String id;
    public Words word;


    CraftedItemPower(int matItems, int perc, String id, Words word) {
        this.perc = perc;
        this.matItems = matItems;
        this.id = id;
        this.word = word;
    }
}
