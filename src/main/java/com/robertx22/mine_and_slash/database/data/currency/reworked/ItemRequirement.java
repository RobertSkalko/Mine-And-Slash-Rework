package com.robertx22.mine_and_slash.database.data.currency.reworked;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public abstract class ItemRequirement {

    public abstract boolean isValid(ItemStack obj);

    public abstract MutableComponent getTooltip();

}
