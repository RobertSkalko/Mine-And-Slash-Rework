package com.robertx22.mine_and_slash.uncommon.item_filters;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.item_filters.bases.ItemFilter;
import net.minecraft.world.item.ItemStack;

public class UniqueItemFilter extends ItemFilter {

    @Override
    public boolean IsValidItem(ItemStack stack) {

        GearItemData gear = StackSaving.GEARS.loadFrom(stack);

        return gear != null && gear.isUnique();

    }
}
