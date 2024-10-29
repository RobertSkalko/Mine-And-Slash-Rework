package com.robertx22.mine_and_slash.itemstack;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public class ExileStacklessData {

    public HashMap<String, Object> map = new HashMap<>();
    public HashMap<String, StackKey> keys = new HashMap<>();

    private transient ExileStack stack = ExileStack.of(new ItemStack(Items.STONE_SWORD));

    
    public <T> T get(StackKey<T> key) {
        return (T) map.get(key.key);
    }

    public <T> T getOrCreate(StackKey<T> key) {
        var o = stack.get(key);
        if (!map.containsKey(key.key)) {
            map.put(key.key, o.getOrCreate());
            keys.put(key.key, key);
        }
        return get(key);
    }


    public <T> void set(StackKey<T> key, T obj) {
        map.put(key.key, obj);
        keys.put(key.key, key);

    }

    public void apply(ExileStack stack) {
        for (Map.Entry<String, Object> en : map.entrySet()) {
            var key = keys.get(en.getKey());
            stack.get(key).set(map.get(key.key));
        }
    }


}
