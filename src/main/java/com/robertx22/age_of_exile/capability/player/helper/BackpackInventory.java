package com.robertx22.age_of_exile.capability.player.helper;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.a_libraries.curios.RefCurio;
import com.robertx22.age_of_exile.capability.player.BackpackItemData;
import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Math;

public class BackpackInventory extends MyInventory {

    Player p;
    Backpacks.BackpackType type;

    public BackpackInventory(Player p, Backpacks.BackpackType type, int pSize) {
        super(pSize);
        this.type = type;
        this.p = p;
    }


    @Override
    public int getTotalSlots() {
        int slots = 9 * 3;

        ItemStack stack = MyCurioUtils.get(RefCurio.BACKPACK, p, 0);
        BackpackItemData data = StackSaving.BACKPACK.loadFrom(stack);
        if (data != null) {
            slots += data.getSlots(type);
        }
        return Math.clamp(slots, 0, getContainerSize());
    }

    // todo must test this
    public void throwOutBlockedSlotItems() {

        int open = getTotalSlots();

        for (int i = 0; i < getContainerSize(); i++) {
            if (i > open) {
                ItemStack stack = getItem(i);
                PlayerUtils.giveItem(stack.copy(), p);
                stack.shrink(100);
            }

        }

    }

    public int getBlockedSlots() {
        return getContainerSize() - getTotalSlots();
    }


}
