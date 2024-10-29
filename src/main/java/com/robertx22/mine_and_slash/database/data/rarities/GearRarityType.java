package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.ChatFormatting;

public enum GearRarityType {
    NORMAL("normal", ChatFormatting.LIGHT_PURPLE, Words.NORMAL_RARITY),
    UNIQUE("unique", ChatFormatting.RED, Words.Unique_Gear),
    RUNED("runed", ChatFormatting.YELLOW, Words.Runed_gear);

    public String id;
    public ChatFormatting color;
    public Words word;

    GearRarityType(String id, ChatFormatting color, Words word) {
        this.id = id;
        this.color = color;
        this.word = word;
    }
}
