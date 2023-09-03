package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CraftingStationMenu extends AbstractContainerMenu {


    public CraftingStationMenu(int pContainerId, Container pContainer) {
        this(pContainerId, pContainer, new SimpleContainer(9), new SimpleContainer(1));
    }


    public CraftingStationMenu(int pContainerId, Container pinv, Container mats, Container result) {
        super(SlashContainers.CRAFTING.get(), pContainerId);


        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(pinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(pinv, l, 8 + l * 18, 142));
        }


        try {

            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    this.addSlot(new Slot(mats, j + i * 3, 30 + j * 18, 17 + i * 18));
                }
            }

            this.addSlot(new Slot(result, 0, 124, 35));


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}