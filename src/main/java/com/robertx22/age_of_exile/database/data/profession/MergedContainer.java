package com.robertx22.age_of_exile.database.data.profession;


import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MergedContainer extends SimpleContainer implements WorldlyContainer {

    public List<Inventory> invs;

    public MergedContainer(List<Inventory> invs) {
        super(getSize(invs));
        this.invs = invs;
    }

    static int getSize(List<Inventory> inv) {
        return inv.stream().mapToInt(x -> x.size).sum();
    }

    public int[] getIndices(String id) {


        int i = 0;

        for (Inventory inv : invs) {
            if (!inv.id.equals(id)) {
                i += inv.size;
            } else {
                int[] ar = new int[inv.size];
                for (int x = 0; x < inv.size; x++) {
                    ar[x] = i + x;
                }
                return ar;
            }
        }

        return new int[0];
    }

    public Container getInventory(Inventory inv) {
        return new SimpleContainer(getAllStacks(inv).toArray(new ItemStack[inv.size]));
    }

    public int getIndex(String id, int num) {
        int i = 0;

        for (Inventory inv : invs) {
            if (!inv.id.equals(id)) {
                i += inv.size;
            } else {
                i += num;
                return i;
            }
        }

        return i;
    }

    public List<ItemStack> getAllStacks(Inventory inv) {
        List<ItemStack> list = new ArrayList<>();
        for (int index : getIndices(inv.id)) {
            list.add(getItem(index));
        }
        return list;

    }

    public ItemStack getStack(Inventory inv, int num) {
        return this.getItem(getIndex(inv.id, num));
    }

    public void setStack(ItemStack stack, Inventory inv, int num) {
        this.setItem(getIndex(inv.id, num), stack);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        Inventory inv = invs.stream().filter(x -> x.hopperface == pSide).findAny().get();
        if (inv != null) {
            return getIndices(inv.id);
        }
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return true;
    }

    public static class Inventory {

        public String id;
        public int size;
        public Direction hopperface;

        public Inventory(String id, int size, Direction hopperface) {
            this.id = id;
            this.size = size;
            this.hopperface = hopperface;
        }
    }


}
