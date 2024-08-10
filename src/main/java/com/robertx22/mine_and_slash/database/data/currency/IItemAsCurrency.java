package com.robertx22.mine_and_slash.database.data.currency;

import com.robertx22.mine_and_slash.database.data.currency.base.Currency;
import net.minecraft.world.item.ItemStack;

public interface IItemAsCurrency {
    Currency currencyEffect(ItemStack stack);

}
