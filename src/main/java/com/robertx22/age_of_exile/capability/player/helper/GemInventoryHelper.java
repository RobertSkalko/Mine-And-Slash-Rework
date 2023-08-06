package com.robertx22.age_of_exile.capability.player.helper;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GemInventoryHelper {
    public static int TOTAL_AURAS = 9;

    public static int MAX_SKILL_GEMS = 8;
    public static int SUPPORT_GEMS_PER_SKILL = 5;
    public static int TOTAL_SLOTS = MAX_SKILL_GEMS * SUPPORT_GEMS_PER_SKILL;


    SimpleContainer inv;
    SimpleContainer auras;

    public GemInventoryHelper(SimpleContainer inv, SimpleContainer auras) {
        this.inv = inv;
        this.auras = auras;


    }

    public SimpleContainer getGemsInv() {
        return inv;
    }

    public SimpleContainer getAuraInv() {
        return auras;
    }

    public ItemStack getSkillGem(int num) {
        int index = num * (SUPPORT_GEMS_PER_SKILL + 1);
        return inv.getItem(index);
    }


    public List<ItemStack> getSupportGems(int num) {

        int index = num * (SUPPORT_GEMS_PER_SKILL + 1);

        var list = new ArrayList<ItemStack>();

        for (int i = index; i < SUPPORT_GEMS_PER_SKILL; i++) {
            list.add(inv.getItem(i));
        }
        return list;

    }

    public List<ItemStack> getAuras() {
        var list = new ArrayList<ItemStack>();

        for (int i = 0; i < auras.getContainerSize(); i++) {
            list.add(auras.getItem(i));
        }
        return list;

    }

}
