package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingStationMenu extends AbstractContainerMenu {


    public CraftingStationMenu(int pContainerId, Container pContainer) {
        this(pContainerId, pContainer, new SimpleContainer(9), new SimpleContainer(9));
    }


    // really only use this for JEI integration
    public List<Slot> matslots = new ArrayList<>();
    public List<Slot> invslots = new ArrayList<>();

    public CraftingStationMenu(int pContainerId, Container pinv, Container mats, Container results) {
        super(SlashContainers.CRAFTING.get(), pContainerId);


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


        try {

            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    var slot = new Slot(mats, j + i * 3, 30 + j * 18 - 17, 17 + i * 18);
                    this.matslots.add(slot);
                    this.addSlot(slot);
                }
            }
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    var slot = new Slot(results, j + i * 3, 111 + j * 18, 17 + i * 18);
                    this.addSlot(slot);
                }
            }

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