package com.robertx22.age_of_exile.database.data.profession;


import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class MergedContainer extends SimpleContainer {

    public List<Inventory> invs;


    public MergedContainer(List<Inventory> invs) {
        super(getSize(invs));
        this.invs = invs;
    }

    static int getSize(List<Inventory> inv) {
        return inv.stream().mapToInt(x -> x.size).sum();
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

    public ItemStack getStack(Inventory inv, int num) {
        return this.getItem(getIndex(inv.id, num));
    }

    public void setStack(ItemStack stack, Inventory inv, int num) {
        this.setItem(getIndex(inv.id, num), stack);
    }

    public static class Inventory {

        public String id;
        public int size;

        public Inventory(String id, int size) {
            this.id = id;
            this.size = size;
        }


    }


}
