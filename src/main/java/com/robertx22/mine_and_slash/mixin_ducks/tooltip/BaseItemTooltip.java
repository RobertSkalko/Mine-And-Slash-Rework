package com.robertx22.mine_and_slash.mixin_ducks.tooltip;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public abstract class BaseItemTooltip extends ItemTooltip {

    Supplier<Item> sup;

    public BaseItemTooltip(Supplier<Item> sup) {
        this.sup = sup;
    }

    @Override
    public boolean shouldRender(ItemStack stack) {
        return stack.getItem() == sup.get();
    }
}
