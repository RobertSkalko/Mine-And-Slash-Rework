package com.robertx22.mine_and_slash.saveclasses.jewel;

import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public enum CraftedJewelTier {

    ZERO(0, 0, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.COMMON_ID).get(), 10), false),
    ONE(1, 1, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.UNCOMMON).get(), 10), true),
    TWO(2, 20, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.RARE_ID).get(), 10), true),
    THREE(3, 40, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.EPIC_ID).get(), 10), false),
    FOUR(4, 60, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.LEGENDARY_ID).get(), 10), false),
    FIVE(5, 80, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.MYTHIC_ID).get(), 10), true),
    LAST(6, 100, () -> new ItemStack(RarityItems.RARITY_STONE.get(IRarity.MYTHIC_ID).get(), 10), false);


    public int tier;
    public int lvl;
    public Supplier<ItemStack> upgradeStack;
    public boolean addsAffix = false;


    CraftedJewelTier(int tier, int lvl, Supplier<ItemStack> upgradeStack, boolean addsAffix) {
        this.tier = tier;
        this.lvl = lvl;
        this.addsAffix = addsAffix;
        this.upgradeStack = upgradeStack;
    }

    public boolean canUpgradeMore() {
        return this != LAST;
    }

    public static CraftedJewelTier fromTier(int t) {
        for (CraftedJewelTier v : CraftedJewelTier.values()) {
            if (v.tier == t) {
                return v;
            }
        }
        return null;
    }
}
