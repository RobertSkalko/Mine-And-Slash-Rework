package com.robertx22.mine_and_slash.a_libraries.jei;

import com.mojang.blaze3d.platform.InputConstants;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import mezz.jei.api.constants.ModIds;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.extensions.IExtendableRecipeCategory;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class CraftingCategory implements IExtendableRecipeCategory<ProfessionRecipe, ICraftingCategoryExtension> {
    public static final int width = 116;
    public static final int height = 54;

    private final IDrawable background;
    private final IDrawable icon;
    private final ICraftingGridHelper craftingGridHelper;

    public static final String TEXTURE_GUI_PATH = "textures/jei/gui/";
    public static final String TEXTURE_GUI_VANILLA = TEXTURE_GUI_PATH + "gui_vanilla.png";

    public static final ResourceLocation RECIPE_GUI_VANILLA = new ResourceLocation(ModIds.JEI_ID, TEXTURE_GUI_VANILLA);

    ICraftingCategoryExtension recipeExtension;

    RecipeType<ProfessionRecipe> type;
    Component word;
    String prof;

    public CraftingCategory(String prof, IGuiHelper guiHelper, RecipeType<ProfessionRecipe> type, Component word) {
        this.prof = prof;

        ResourceLocation location = RECIPE_GUI_VANILLA;
        background = guiHelper.createDrawable(location, 0, 60, width, height);

        icon = guiHelper.createDrawableItemStack(new ItemStack(SlashItems.STATIONS.get(prof).get()));

        this.word = word;
        this.type = type;

        craftingGridHelper = guiHelper.createCraftingGridHelper();
    }

    @Override
    public RecipeType<ProfessionRecipe> getRecipeType() {
        return type;
    }

    @Override
    public Component getTitle() {
        return word;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ProfessionRecipe recipe, IFocusGroup focuses) {
        recipeExtension = new CraftingExtension(recipe);
        recipeExtension.setRecipe(builder, craftingGridHelper, focuses);
    }

    @Override
    public void draw(ProfessionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int recipeWidth = this.getWidth();
        int recipeHeight = this.getHeight();
        recipeExtension.drawInfo(recipeWidth, recipeHeight, guiGraphics, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(ProfessionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {

        List<Component> list = new ArrayList<>();

        int diffx = (int) Math.abs(mouseX - 70);
        int diffy = (int) Math.abs(mouseY - 22);

        if (diffx < 10 && diffy < 10) {
            list.addAll(recipe.getTooltipJEI());
        }
        list.addAll(recipeExtension.getTooltipStrings(mouseX, mouseY));

        return list;
    }

    @Override
    public boolean handleInput(ProfessionRecipe recipe, double mouseX, double mouseY, InputConstants.Key input) {

        return recipeExtension.handleInput(mouseX, mouseY, input);
    }

    @Override
    public boolean isHandled(ProfessionRecipe recipe) {
        return true;
    }

    @Override
    public <R extends ProfessionRecipe> void addCategoryExtension(Class<? extends R> recipeClass, Function<R, ? extends ICraftingCategoryExtension> extensionFactory) {

        // todo wtf is this

        //     recipeExtension.addRecipeExtensionFactory(recipeClass, null, extensionFactory);
    }

    @Override
    public <R extends ProfessionRecipe> void addCategoryExtension(Class<? extends R> recipeClass, Predicate<R> extensionFilter, Function<R, ? extends ICraftingCategoryExtension> extensionFactory) {
        //  recipeExtension.addRecipeExtensionFactory(recipeClass, extensionFilter, extensionFactory);
    }

    @Override
    public ResourceLocation getRegistryName(ProfessionRecipe recipe) {
        return new ResourceLocation(recipe.result);
    }
}