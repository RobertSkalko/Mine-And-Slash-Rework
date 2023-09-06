package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.function.Function;

public class CraftedItemHolder {

    private HashMap<CraftedItemPower, RegObj<Item>> map = new HashMap<>();

    public CraftedItemHolder(String id, Maker maker) {
        for (CraftedItemPower power : CraftedItemPower.values()) {
            map.put(power, Def.item(maker.folder + "/" + power.id + "_" + id, () -> maker.fun.apply(power)));
        }
    }

    public static class Maker {
        public String folder;
        public Function<CraftedItemPower, Item> fun;

        public Maker(String folder, Function<CraftedItemPower, Item> fun) {
            this.folder = folder;
            this.fun = fun;
        }
    }


    public ItemStack get(SkillItemTier tier, CraftedItemPower power) {
        try {
            ItemStack stack = new ItemStack(map.get(power).get());
            LeveledItem.setTier(stack, tier.tier);
            return stack;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ItemStack.EMPTY;
    }

}
