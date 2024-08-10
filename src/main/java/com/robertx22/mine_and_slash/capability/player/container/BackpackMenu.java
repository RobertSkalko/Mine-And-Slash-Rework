package com.robertx22.mine_and_slash.capability.player.container;

import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BackpackMenu extends AbstractContainerMenu {

    int size = 6 * 9;
    int containerRows;

    public BackpackMenu(int pContainerId, Inventory inv) {
        this(Backpacks.BackpackType.GEARS, pContainerId, inv, new SimpleContainer(6 * 9), 6);
    }

    Backpacks.BackpackType type;

    public BackpackMenu(Backpacks.BackpackType type, int pContainerId, Container playerINV, Container backpackINV, int containerRows) {
        super(SlashContainers.BACKPACK.get(), pContainerId);
        this.containerRows = 6;
        this.size = containerRows * 9;
        this.type = type;

        try {
            int i = (containerRows - 4) * 18;

            for (int j = 0; j < containerRows; ++j) {
                for (int k = 0; k < 9; ++k) {
                    this.addSlot(new BackpackSlot(backpackINV, k + j * 9, 8 + k * 18, 18 + j * 18));
                }
            }
            for (int l = 0; l < 3; ++l) {
                for (int j1 = 0; j1 < 9; ++j1) {
                    this.addSlot(new Slot(playerINV, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
                }
            }

            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerINV, i1, 8 + i1 * 18, 161 + i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            if (!type.isValid(itemstack1)) {
                return ItemStack.EMPTY;
            }
            itemstack = itemstack1.copy();
            if (pIndex < size) {
                if (!this.moveItemStackTo(itemstack1, size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, size, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }


        return itemstack;
    }

    public class BackpackSlot extends Slot {

        public BackpackSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return type.isValid(pStack);
        }
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
