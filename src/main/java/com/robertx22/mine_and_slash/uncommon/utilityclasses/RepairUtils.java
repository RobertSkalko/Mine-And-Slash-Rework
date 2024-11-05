package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import net.minecraft.world.item.ItemStack;

public class RepairUtils {

    public static boolean isItemBroken(ItemStack stack) {
        if (!stack.isDamageableItem()) {
            return false;
        }
        if (stack.getMaxDamage() == 1) {
            // some weirdos use 1 durability item mods
            return stack.getDamageValue() < 1;
        }
        return stack.getDamageValue() >= stack.getMaxDamage() - 10;
    }

}
