package com.robertx22.mine_and_slash.database.data.currency.base;

import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;

public interface IShapedRecipe {
    ShapedRecipeBuilder getRecipe();

    default ShapedRecipeBuilder shaped(ItemLike pro) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pro, 1);
    }

    default ShapedRecipeBuilder shaped(ItemLike pro, int i) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pro, i);
    }

    default CriterionTriggerInstance trigger() {
        return EnchantedItemTrigger.TriggerInstance.enchantedItem();
    }

    static ShapedRecipeBuilder of(ItemLike item, int count) {
        return ShapedRecipeBuilder.shaped(RecipeCategory.MISC, item, count)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem());

    }
}



