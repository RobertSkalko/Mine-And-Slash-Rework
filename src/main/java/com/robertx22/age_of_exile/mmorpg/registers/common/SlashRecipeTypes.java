package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class SlashRecipeTypes {
    // todo
    // public static RegObj<RecipeType<?>> COOKING = Def.recipeType("crafting", () -> new CraftingRecipe("cooking"));
    //public static RegObj<RecipeType<?>> ALCHEMY = Def.recipeType("alchemy", () -> new CraftingRecipe("alchemy"));
    //public static RegObj<RecipeType<?>> ENCHANTING = Def.recipeType("enchanting", () -> new CraftingRecipe("enchanting"));


    public static void init() {

    }

    static <T extends Recipe<?>> RecipeType<T> register(final String string) {
        return Registry.register(BuiltInRegistries.RECIPE_TYPE, (ResourceLocation) SlashRef.id(string), new RecipeType<T>() {
            @Override
            public String toString() {
                return string;
            }
        });
    }
}
