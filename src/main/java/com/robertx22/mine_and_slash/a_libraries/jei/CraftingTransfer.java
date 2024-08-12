package com.robertx22.mine_and_slash.a_libraries.jei;

import com.robertx22.mine_and_slash.database.data.profession.ProfessionRecipe;
import com.robertx22.mine_and_slash.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;

import java.util.List;
import java.util.Optional;

public class CraftingTransfer implements IRecipeTransferInfo<CraftingStationMenu, ProfessionRecipe> {
    RecipeType<ProfessionRecipe> type;
    String prof;

    public CraftingTransfer(String prof, RecipeType<ProfessionRecipe> type) {
        this.type = type;
        this.prof = prof;
    }


    @Override
    public Class<? extends CraftingStationMenu> getContainerClass() {
        return CraftingStationMenu.class;
    }

    @Override
    public Optional<MenuType<CraftingStationMenu>> getMenuType() {
        return Optional.of(SlashContainers.STATIONS.get(prof).get());
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

    @Override
    public boolean requireCompleteSets(CraftingStationMenu container, ProfessionRecipe recipe) {
        return false; // put a whole stack if possible of each required item
    }
}
