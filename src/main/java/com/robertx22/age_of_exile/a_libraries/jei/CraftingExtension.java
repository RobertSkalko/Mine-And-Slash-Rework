package com.robertx22.age_of_exile.a_libraries.jei;

import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftingExtension implements ICraftingCategoryExtension {
    protected final ProfessionRecipe recipe;

    public CraftingExtension(ProfessionRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        for (ItemStack m : recipe.getMaterials()) {
            inputs.add(Arrays.asList(m));
        }

        ItemStack resultItem = recipe.toResultStackForJei();

        int width = getWidth();
        int height = getHeight();

        craftingGridHelper.createAndSetOutputs(builder, List.of(resultItem));
        craftingGridHelper.createAndSetInputs(builder, inputs, width, height);
        
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return new ResourceLocation(recipe.result);
    }

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }
}
