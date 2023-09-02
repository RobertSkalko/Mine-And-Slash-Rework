package com.robertx22.age_of_exile.database.data.profession;

import net.minecraft.world.item.ItemStack;

public class LeveledItem {


    public static int getLevel(ItemStack stack) {

        if (stack.hasTag()) {
            return stack.getOrCreateTag().getInt("level");
        }

        return 0;
    }

    public static void setLevel(ItemStack stack, int lvl) {
        stack.getOrCreateTag().putInt("level", lvl);
    }

    public static int getTier(ItemStack stack) {

        if (stack.hasTag()) {
            return stack.getOrCreateTag().getInt("tier");
        }

        return 0;
    }

    public static void setTier(ItemStack stack, int lvl) {
        stack.getOrCreateTag().putInt("tier", lvl);
    }
}
