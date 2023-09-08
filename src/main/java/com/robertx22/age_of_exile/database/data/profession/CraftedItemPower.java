package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.ChatFormatting;

import java.util.Arrays;
import java.util.Comparator;

public enum CraftedItemPower {

    LESSER(1000, IRarity.UNCOMMON, 1, 0, "lesser", Words.LESSER, ChatFormatting.GREEN),
    MEDIUM(250, IRarity.EPIC_ID, 3, 50, "medium", Words.MEDIUM, ChatFormatting.BLUE),
    GREATER(50, IRarity.MYTHIC_ID, 5, 100, "greater", Words.GREATER, ChatFormatting.LIGHT_PURPLE);

    public int weight;
    public ChatFormatting color;
    public int matItems;
    public int perc;
    public String id;
    public Words word;

    public String rar;

    CraftedItemPower(int we, String rar, int matItems, int perc, String id, Words word, ChatFormatting color) {
        this.color = color;
        this.weight = we;
        this.rar = rar;
        this.perc = perc;
        this.matItems = matItems;
        this.id = id;
        this.word = word;
    }

    public static CraftedItemPower ofId(String id) {
        return Arrays.stream(values()).filter(x -> x.id.equals(id)).findAny().orElse(LESSER);
    }

    // test if this works
    public static CraftedItemPower ofRarity(String rar) {

        return Arrays.stream(CraftedItemPower.values()).filter(x -> {
            GearRarity test = x.getRarity();
            GearRarity check = ExileDB.GearRarities().get(rar);

            return check.item_tier >= test.item_tier;
        }).max(Comparator.comparingInt(x -> x.perc)).orElse(CraftedItemPower.LESSER);

    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }
}
