package com.robertx22.age_of_exile.uncommon.item_filters;

import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilter;
import net.minecraft.world.item.ItemStack;

public class GearItemFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {
        return StackSaving.GEARS.has(stack);
    }
}
