package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;

public class VanillaRarities {

    public static Rarity LEGENDARY = Rarity.create("LEGENDARY", ChatFormatting.GOLD);
    public static Rarity MYTHIC = Rarity.create("MYTHIC", ChatFormatting.DARK_PURPLE);
    public static Rarity UNIQUE = Rarity.create("UNIQUE", ChatFormatting.RED);
    public static Rarity RUNED = Rarity.create("RUNED", ChatFormatting.YELLOW);
    public static Rarity UNCOMMON = Rarity.create("UNCOMMON_ITEM", ChatFormatting.GREEN);

    public static void init() {


    }

}
