package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.world.item.ItemStack;

public class RepairUtils {

    public static boolean isItemBroken(ItemStack stack) {
        if (!stack.isDamageableItem()) {
            return false;
        }
        return stack.getDamageValue() >= stack.getMaxDamage() - 10;
    }

}
