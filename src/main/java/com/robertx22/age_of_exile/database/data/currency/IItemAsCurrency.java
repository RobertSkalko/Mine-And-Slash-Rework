package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import net.minecraft.world.item.ItemStack;

public interface IItemAsCurrency {
    Currency currencyEffect(ItemStack stack);

}
