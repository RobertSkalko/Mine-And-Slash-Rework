package com.robertx22.mine_and_slash.database.data.currency.base;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

public interface IShapelessRecipe {

    ShapelessRecipeBuilder getRecipe();

    default CriterionTriggerInstance trigger() {
        return EnchantedItemTrigger.TriggerInstance.enchantedItem();
    }
}



