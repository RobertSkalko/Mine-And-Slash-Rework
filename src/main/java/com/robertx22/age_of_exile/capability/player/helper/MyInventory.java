package com.robertx22.age_of_exile.capability.player.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

// had to override tag methods because the simplecontainer doesn't save place in inventory, just autosorts items..
public class MyInventory extends SimpleContainer {


    public MyInventory(int pSize) {
        super(pSize);

    }


    @Override
    public void fromTag(ListTag pContainerNbt) {
        for (int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for (int k = 0; k < pContainerNbt.size(); ++k) {
            CompoundTag compoundtag = pContainerNbt.getCompound(k);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < this.getContainerSize()) {
                this.setItem(j, ItemStack.of(compoundtag));
            }
        }

    }

    @Override
    public ListTag createTag() {
        ListTag listtag = new ListTag();

        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte) i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }

        return listtag;
    }

    public int getTotalSlots() {
        return this.getContainerSize(); // todo for upgradables maybe limit this
    }

    public boolean hasFreeSlots() {
        return getFreeSlots() > 0;
    }


    public int getFreeSlots() {
        int free = 0;
        for (int i = 0; i < this.getTotalSlots(); i++) {
            ItemStack stack = this.getItem(i);
            if (stack.isEmpty()) {
                free++;
            }
        }
        return free;
    }

}
