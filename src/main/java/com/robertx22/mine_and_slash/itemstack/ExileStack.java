package com.robertx22.mine_and_slash.itemstack;

import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExileStack {

    public HashMap<String, StackData> map = new HashMap<>();

    private ItemStack stack;

    // stacks are always copied and if you want to modify it, you grab the result
    public static ExileStack of(ItemStack stack) {

        ExileStack b = new ExileStack();
        if (stack != null) {
            b.stack = stack.copy();
        }
        return b;
    }

    public List<StackData> getAll() {
        return map.values().stream().toList();
    }

    public <T> StackData<T> get(StackKey<T> key) {
        if (!map.containsKey(key.key)) {
            map.put(key.key, key.sup.apply(this));
        }
        return map.get(key.key);
    }


    public boolean stackWasChanged = false;

    public ItemStack getStack() {
        return stack;
    }

    public boolean isCorrupted() {
        return get(StackKeys.CUSTOM).has() && get(StackKeys.CUSTOM).getOrCreate().isCorrupted();
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        stackWasChanged = true;

        for (Map.Entry<String, StackData> en : map.entrySet()) {
            en.getValue().resetGetterCache();
        }
    }
}
