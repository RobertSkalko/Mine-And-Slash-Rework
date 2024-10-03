package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// todo if i ever wanted to split the mod into parts, this would be impossible to split..
// maybe a key?
public class ExileStack {


    // todo switch all these to stackkeys
    private HashMap<String, StackData> map = new HashMap<>();

    // todo on major version, SEPARATE stuff like levels, rarities into their own components
    public StackData<GearItemData> GEAR = new StackData<>(this, StackSaving.GEARS);
    public StackData<JewelItemData> JEWEL = new StackData<>(this, StackSaving.JEWEL);
    public StackData<MapItemData> MAP = new StackData<>(this, StackSaving.MAP);
    public StackData<OmenData> OMEN = new StackData<>(this, StackSaving.OMEN);
    public StackData<PotentialData> POTENTIAL = new StackData<>(this, StackSaving.POTENTIAL);
    public StackData<CustomItemData> CUSTOM = new StackData<>(this, StackSaving.CUSTOM_DATA);

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
        return CUSTOM.has() && CUSTOM.getOrCreate().isCorrupted();
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        stackWasChanged = true;

        for (Map.Entry<String, StackData> en : map.entrySet()) {
            en.getValue().resetGetterCache();
        }
    }
}
