package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.temp.SkillItemTier;
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

    public static int getTierNum(ItemStack stack) {

        if (stack.hasTag()) {
            return stack.getOrCreateTag().getInt("tier");
        }

        return 0;
    }

    public static SkillItemTier getTier(ItemStack stack) {
        return SkillItemTier.of(getTierNum(stack));
    }

    public static void setTier(ItemStack stack, int lvl) {
        stack.getOrCreateTag().putInt("tier", lvl);
    }
}
