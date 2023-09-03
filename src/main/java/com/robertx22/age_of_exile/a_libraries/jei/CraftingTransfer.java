package com.robertx22.age_of_exile.a_libraries.jei;

import com.robertx22.age_of_exile.database.data.profession.CraftingStationMenu;
import com.robertx22.age_of_exile.database.data.profession.ProfessionRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.List;
import java.util.Optional;

public class CraftingTransfer implements IRecipeTransferInfo<CraftingStationMenu, ProfessionRecipe> {
    RecipeType<ProfessionRecipe> type;

    public CraftingTransfer(RecipeType<ProfessionRecipe> type) {
        this.type = type;
    }


    @Override
    public Class<? extends CraftingStationMenu> getContainerClass() {
        return CraftingStationMenu.class;
    }

    @Override
    public Optional<MenuType<CraftingStationMenu>> getMenuType() {
        return Optional.of(SlashContainers.CRAFTING.get());
    }

    @Override
    public RecipeType getRecipeType() {
        return type;
    }

    @Override
    public boolean canHandle(CraftingStationMenu container, ProfessionRecipe recipe) {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(CraftingStationMenu container, ProfessionRecipe recipe) {
        return container.matslots;
    }

    @Override
    public List<Slot> getInventorySlots(CraftingStationMenu m, ProfessionRecipe recipe) {
        return m.invslots;

    }
}
