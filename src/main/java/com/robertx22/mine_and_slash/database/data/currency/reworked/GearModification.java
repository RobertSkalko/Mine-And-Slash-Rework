package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

public abstract class GearModification extends ItemModification {
    public abstract void modifyGear(GearItemData gear);

    @Override
    public void apply(ItemStack stack) {
        var gear = StackSaving.GEARS.loadFrom(stack);

        if (gear != null) {
            modifyGear(gear);
            gear.saveToStack(stack);
        }
    }

}
