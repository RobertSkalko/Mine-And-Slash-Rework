package com.robertx22.age_of_exile.uncommon.item_filters;

import com.robertx22.age_of_exile.database.data.currency.IItemAsCurrency;
import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilter;
import net.minecraft.world.item.ItemStack;

public class CurrencyItemEffectFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {
        return stack.getItem() instanceof IItemAsCurrency;
    }
}

