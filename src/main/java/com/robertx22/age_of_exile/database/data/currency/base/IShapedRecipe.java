package com.robertx22.age_of_exile.database.data.currency.base;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;

public interface IShapedRecipe {
    ShapedRecipeBuilder getRecipe();

    default ShapedRecipeBuilder shaped(ItemLike pro) {
        return ShapedRecipeBuilder.shaped(pro, 1);
    }

    default ShapedRecipeBuilder shaped(ItemLike pro, int i) {
        return ShapedRecipeBuilder.shaped(pro, i);
    }

    default CriterionTriggerInstance trigger() {
        return EnchantedItemTrigger.TriggerInstance.enchantedItem();
    }
}



