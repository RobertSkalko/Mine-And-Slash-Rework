package com.robertx22.mine_and_slash.database.data.currency.reworked;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public abstract class ItemModification {

    public abstract void apply(ItemStack stack);

    public abstract MutableComponent getTooltip();

}
