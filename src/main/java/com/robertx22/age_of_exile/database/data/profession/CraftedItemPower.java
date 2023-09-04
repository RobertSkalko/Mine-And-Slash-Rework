package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;

import java.util.Arrays;

public enum CraftedItemPower {

    LESSER(1, 0, "lesser", Words.LESSER, ChatFormatting.GREEN),
    MEDIUM(3, 50, "medium", Words.MEDIUM, ChatFormatting.BLUE),
    GREATER(5, 100, "greater", Words.GREATER, ChatFormatting.LIGHT_PURPLE);

    public ChatFormatting color;
    public int matItems;
    public int perc;
    public String id;
    public Words word;


    CraftedItemPower(int matItems, int perc, String id, Words word, ChatFormatting color) {
        this.color = color;
        this.perc = perc;
        this.matItems = matItems;
        this.id = id;
        this.word = word;
    }

    public static CraftedItemPower ofId(String id) {
        return Arrays.stream(values()).filter(x -> x.id.equals(id)).findAny().orElse(LESSER);
    }
}
