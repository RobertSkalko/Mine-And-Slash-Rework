package com.robertx22.age_of_exile.uncommon.item_filters;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.item_filters.bases.ItemFilter;
import net.minecraft.world.item.ItemStack;

public class UniqueItemFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {

        GearItemData gear = StackSaving.GEARS.loadFrom(stack);

        return gear != null && gear.isUnique();

    }
}
