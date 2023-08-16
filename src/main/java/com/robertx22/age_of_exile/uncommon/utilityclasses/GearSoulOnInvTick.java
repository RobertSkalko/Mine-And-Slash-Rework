package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GearSoulOnInvTick {

    public static void checkAndGenerate(Player player) {
/*
        try {

            if (player.level().isClientSide) {
                return;
            }

            for (ItemStack stack : player.getInventory().items) {
                tryStack(player, stack);
            }
            for (ItemStack stack : player.getInventory().armor) {
                tryStack(player, stack);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


 */
    }

    static void tryStack(Player player, ItemStack stack) {
        /*
        if (stack.isEmpty()) {
            return;
        }
        // todo why is this neeeded

        if (StackSaving.STAT_SOULS.has(stack)) {
            StatSoulData soul = StackSaving.STAT_SOULS.loadFrom(stack);

            if (soul != null && soul.canInsertIntoStack(stack)) {
                try {
                    GearItemData gear = soul.createGearData(stack);
                    stack.removeTagKey(StatSoulItem.TAG);
                    gear.saveToStack(stack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

         */

    }

}
