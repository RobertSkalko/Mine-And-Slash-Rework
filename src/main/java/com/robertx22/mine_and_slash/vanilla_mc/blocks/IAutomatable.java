package com.robertx22.mine_and_slash.vanilla_mc.blocks;

import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface IAutomatable {

    List<Integer> getInputSlots();

    List<Integer> getOutputSlots();

    List<Integer> getFuelSlots();

    default boolean isInputSlot(int index) {
        return getInputSlots().contains(index);
    }

    default boolean isOutputSlot(int index) {
        return getOutputSlots().contains(index);
    }

    default boolean isFuelSlot(int index) {
        return getFuelSlots().contains(index);
    }

    default boolean isValidInput(ItemStack stack) {
        return true;
    }

    default boolean isValidOuput(ItemStack stack) {
        return true;
    }

    default boolean isValidFuel(ItemStack stack) {
        return true;
    }

}
