package com.robertx22.mine_and_slash.database.data.profession;

import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ICreativeTabNbt;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface ICreativeTabTiered extends ICreativeTabNbt {

    public Item getThis();

    @Override
    default List<ItemStack> createAllVariationsForCreativeTabs() {
        return Arrays.stream(SkillItemTier.values()).map(x -> {
            var stack = getThis().getDefaultInstance();
            LeveledItem.setTier(stack, x.tier);
            return stack;
        }).collect(Collectors.toList());

    }
}
