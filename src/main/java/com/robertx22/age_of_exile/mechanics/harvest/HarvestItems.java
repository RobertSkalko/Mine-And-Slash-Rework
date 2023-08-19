package com.robertx22.age_of_exile.mechanics.harvest;

import com.robertx22.age_of_exile.mechanics.harvest.vanilla.HarvestMaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.SimpleMatItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class HarvestItems {

    static String BLUE_NAME = "Lucid";
    static String PURPLE_NAME = "Chaotic";
    static String GREEN_NAME = "Primal";

    public static RegObj<Item> BLUE_PLANT = Def.item(() -> new HarvestMaterialItem(BLUE_NAME + " Extract"), "harvest/blue_plant");
    public static RegObj<Item> GREEN_PLANT = Def.item(() -> new HarvestMaterialItem(GREEN_NAME + " Extract"), "harvest/green_plant");
    public static RegObj<Item> PURPLE_PLANT = Def.item(() -> new HarvestMaterialItem(PURPLE_NAME + " Extract"), "harvest/purple_plant");

    public static RegObj<Item> BLUE_INGOT = Def.item(() -> new HarvestMaterialItem(BLUE_NAME + " Ingot"), "harvest/blue_ingot");
    public static RegObj<Item> GREEN_INGOT = Def.item(() -> new HarvestMaterialItem(GREEN_NAME + " Ingot"), "harvest/green_ingot");
    public static RegObj<Item> PURPLE_INGOT = Def.item(() -> new HarvestMaterialItem(PURPLE_NAME + " Ingot"), "harvest/purple_ingot");

    public static RegObj<Item> BLUE_KEY = Def.item(() -> new HarvestMaterialItem(BLUE_NAME + " Key"), "harvest/blue_key");
    public static RegObj<Item> GREEN_KEY = Def.item(() -> new HarvestMaterialItem(GREEN_NAME + " Key"), "harvest/green_key");
    public static RegObj<Item> PURPLE_KEY = Def.item(() -> new HarvestMaterialItem(PURPLE_NAME + " Key"), "harvest/purple_key");

    public static RegObj<BlockItem> PURPLE_PLANT_SEED = Def.item(() -> new BlockItem(HarvestBlocks.PURPLE.get(), new Item.Properties()), "harvest/purple_seed");
    public static RegObj<BlockItem> BLUE_PLANT_SEED = Def.item(() -> new BlockItem(HarvestBlocks.BLUE.get(), new Item.Properties()), "harvest/blue_seed");
    public static RegObj<BlockItem> GREEN_PLANT_SEED = Def.item(() -> new BlockItem(HarvestBlocks.GREEN.get(), new Item.Properties()), "harvest/green_seed");

    public static RegObj<Item> CHEST_TEST = Def.item(() -> new SimpleMatItem(), "chest/harvest_blue");

    public static void init() {

    }
}
