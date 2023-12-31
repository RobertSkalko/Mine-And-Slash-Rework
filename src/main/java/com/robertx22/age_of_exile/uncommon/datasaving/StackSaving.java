package com.robertx22.age_of_exile.uncommon.datasaving;

import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftedConsumableData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.CraftingProcessData;
import com.robertx22.age_of_exile.player_skills.ingredient.data.IngredientData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulData;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateData;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.rarity_gear.RarityGearCrateData;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

public class StackSaving {

    public static ItemstackDataSaver<GearItemData> GEARS = of(new ItemstackDataSaver<>("GEAR_ITEM_DATA", GearItemData.class, () -> new GearItemData()));
    public static ItemstackDataSaver<StatSoulData> STAT_SOULS = of(new ItemstackDataSaver<>("stat_soul", StatSoulData.class, () -> new StatSoulData()));
    public static ItemstackDataSaver<LootCrateData> GEM_CRATE = of(new ItemstackDataSaver<>("loot_crate", LootCrateData.class, () -> new LootCrateData()));
    public static ItemstackDataSaver<RarityGearCrateData> RARITY_GEAR_CRATE = of(new ItemstackDataSaver<>("rarity_gear_crate", RarityGearCrateData.class, () -> new RarityGearCrateData()));
    public static ItemstackDataSaver<IngredientData> INGREDIENTS = of(new ItemstackDataSaver<>("ingredient", IngredientData.class, () -> new IngredientData()));
    public static ItemstackDataSaver<CraftedConsumableData> CRAFTED_CONSUMABLE = of(new ItemstackDataSaver<>("craft_cons", CraftedConsumableData.class, () -> new CraftedConsumableData()));
    public static ItemstackDataSaver<CraftingProcessData> CRAFT_PROCESS = of(new ItemstackDataSaver<>("craft_proc", CraftingProcessData.class, () -> new CraftingProcessData()));

    static ItemstackDataSaver of(ItemstackDataSaver t) {
        return t;
    }

    public static void init() {

    }
}
