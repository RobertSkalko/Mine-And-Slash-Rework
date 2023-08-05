package com.robertx22.age_of_exile.vanilla_mc.blocks.bases;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;

public class VanillaFuelSlot extends Slot {
    public VanillaFuelSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return FurnaceBlockEntity.getFuel()
            .getOrDefault(stack.getItem(), 0) > 0;
    }

}
