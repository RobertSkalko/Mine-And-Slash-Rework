package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

import java.util.HashMap;

public class VanillaRarities {


    public static Rarity LEGENDARY_ITEM = Rarity.create("LEGENDARY_ITEM", ChatFormatting.GOLD);
    public static Rarity MYTHIC_ITEM = Rarity.create("MYTHIC_ITEM", ChatFormatting.DARK_PURPLE);
    public static Rarity UNIQUE_ITEM = Rarity.create("UNIQUE_ITEM", ChatFormatting.RED);
    public static Rarity RUNED_ITEM = Rarity.create("RUNED_ITEM", ChatFormatting.YELLOW);
    public static Rarity UNCOMMON_ITEM = Rarity.create("UNCOMMON_ITEM", ChatFormatting.GREEN);


    public static HashMap<String, Rarity> MAP = new HashMap<>();

    public static void init() {

        MAP.put(IRarity.COMMON_ID, Rarity.COMMON);
        MAP.put(IRarity.UNCOMMON, UNCOMMON_ITEM);
        MAP.put(IRarity.RARE_ID, Rarity.RARE);
        MAP.put(IRarity.EPIC_ID, Rarity.EPIC);
        MAP.put(IRarity.LEGENDARY_ID, LEGENDARY_ITEM);
        MAP.put(IRarity.MYTHIC_ID, MYTHIC_ITEM);

        MAP.put(IRarity.UNIQUE_ID, UNIQUE_ITEM);
        MAP.put(IRarity.RUNEWORD_ID, RUNED_ITEM);

    }

}
