package com.robertx22.mine_and_slash.database.data.currency;

import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import net.minecraft.world.item.ItemStack;

public interface IItemAsCurrency {
    CodeCurrency currencyEffect(ItemStack stack);

}
