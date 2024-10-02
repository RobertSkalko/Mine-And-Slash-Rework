package com.robertx22.mine_and_slash.saveclasses.stat_soul;

import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.PotentialData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

// i guess just manually save all relevant stuff for now
public class SavedGearSoul {

    public GearItemData gear;
    public PotentialData potential;
    public CustomItemData custom;

    public SavedGearSoul(GearItemData gear, PotentialData potential, CustomItemData custom) {
        this.gear = gear;
        this.potential = potential;
        this.custom = custom;
    }

    public void saveTo(ItemStack stack) {

        StackSaving.GEARS.saveTo(stack, gear);
        StackSaving.POTENTIAL.saveTo(stack, potential);
        StackSaving.CUSTOM_DATA.saveTo(stack, custom);

    }


}
