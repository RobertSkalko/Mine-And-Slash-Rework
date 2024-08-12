package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.function.Function;

public class RarityItemHolder {

    public HashMap<String, RegObj<Item>> map = new HashMap<>();

    public RarityItemHolder(String id, Maker maker) {
        for (CraftedRarity rar : CraftedRarity.values()) {
            map.put(rar.id, Def.item(maker.folder + "/" + rar.id + "_" + id, () -> maker.fun.apply(rar)));
        }
    }


    public static class Maker {
        public String folder;
        public Function<CraftedRarity, Item> fun;

        public Maker(String folder, Function<CraftedRarity, Item> fun) {
            this.folder = folder;
            this.fun = fun;
        }
    }

    public Item get(String rar) {
        return map.get(rar).get();
    }

    public ItemStack create(SkillItemTier tier, CraftedRarity power) {
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
