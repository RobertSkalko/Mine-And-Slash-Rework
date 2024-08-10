package com.robertx22.mine_and_slash.database.data.profession.screen;

import com.robertx22.mine_and_slash.database.data.profession.Crafting_State;
import com.robertx22.mine_and_slash.database.data.profession.ProfessionBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingStationMenu extends AbstractContainerMenu {

    // really only use this for JEI integration
    public List<Slot> matslots = new ArrayList<>();
    public List<Slot> invslots = new ArrayList<>();

    int size = 19;

    public ProfessionBlockEntity be;

    public CraftingStationMenu(int pContainerId, Container pContainer) {
        this(pContainerId, pContainer, new SimpleContainer(18), new SimpleContainer(1));
    }

    public CraftingStationMenu(int pContainerId, Container pContainer, ProfessionBlockEntity be) {
        this(pContainerId, pContainer, be.inventory, be.show);
        this.be = be;
    }

    private CraftingStationMenu(int pContainerId, Container pinv, Container inv, Container show) {
        super(SlashContainers.CRAFTING.get(), pContainerId);

        //Player Inventory
        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                var slot = new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18);
                invslots.add(slot);
                this.addSlot(slot);
            }
        }

        for (int l = 0; l < 9; ++l) {
            var slot = new Slot(pinv, l, 8 + l * 18, 142);
            invslots.add(slot);
            this.addSlot(slot);
        }

        addSlot(new ShowSlot(show, 0, 80, 16));//80, 56

        //Menu Inventory
        try {

            int index = 0;

            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    var slot = new TogglableSlot(inv, index, 30 + j * 18 - 17, 17 + i * 18);
                    this.matslots.add(slot);
                    this.addSlot(slot);
                    index++;
                }
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    var slot = new Slot(inv, index, 111 + j * 18, 17 + i * 18);
                    this.addSlot(slot);
                    index++;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

  
    static class ShowSlot extends Slot {

        public ShowSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);

        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player pPlayer) {
            return false;
        }

        @Override
        public boolean isHighlightable() {
            return false;
        }

    }

    class TogglableSlot extends Slot {

        public TogglableSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);

        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            if (be == null) {
                return true; // todo why would this be null
            }
            if (be.recipe_locked) {
                if (be.last_recipe == null)
                    return false;

                for (ItemStack item : be.last_recipe.getMaterials()) {
                    if (item.getItem() == pStack.getItem()) {
                        if (be.craftingState == Crafting_State.IDLE)
                            be.craftingState = Crafting_State.ACTIVE;
                        return true;
                    }
                }
                return false;
            }
            if (be.craftingState == Crafting_State.IDLE)
                be.craftingState = Crafting_State.ACTIVE;
            return true;
        }

        @Override
        public boolean mayPickup(Player pPlayer) {
            return true;
        }

        @Override
        public boolean isHighlightable() {
            return true;
        }

    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex > 36) {
                if (!this.moveItemStackTo(itemstack1, 0, 35, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        if (be != null) {
            if (be.craftingState == Crafting_State.IDLE)
                be.craftingState = Crafting_State.ACTIVE;
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}