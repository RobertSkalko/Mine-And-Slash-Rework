package com.robertx22.mine_and_slash.itemstack;

import com.robertx22.mine_and_slash.database.data.omen.OmenData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.jewel.JewelItemData;
import com.robertx22.mine_and_slash.saveclasses.prof_tool.ProfessionToolData;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExileStack {

    public List<StackData> allDatas = new ArrayList<>();

    // todo on major version, SEPARATE stuff like levels, rarities into their own components
    public StackData<GearItemData> GEAR = new StackData<>(this, StackSaving.GEARS);
    public StackData<JewelItemData> JEWEL = new StackData<>(this, StackSaving.JEWEL);
    public StackData<MapItemData> MAP = new StackData<>(this, StackSaving.MAP);
    public StackData<OmenData> OMEN = new StackData<>(this, StackSaving.OMEN);
    public StackData<PotentialData> POTENTIAL = new StackData<>(this, StackSaving.POTENTIAL);
    public StackData<ProfessionToolData> TOOL = new StackData<>(this, StackSaving.TOOL);
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
        for (StackData data : allDatas) {
            data.resetGetterCache();
        }
    }
}
