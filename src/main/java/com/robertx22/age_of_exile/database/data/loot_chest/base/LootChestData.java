package com.robertx22.age_of_exile.database.data.loot_chest.base;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LootChestData {

    public int num = 1;
    public String rar = "";
    public int lvl = 1;
    //public String key = "";
    public String id = "";


    public LootChest getLootChest() {
        return ExileDB.LootChests().get(id);
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public boolean isLocked() {
        return getLootChest().getKey() != null;
    }

    public Item getKeyItem() {
        return getLootChest().getKey();
    }

    public boolean canOpen(Player p) {

        if (!isLocked()) {
            return true;
        }
        return p.getInventory().countItem(getKeyItem()) > 0;
    }

    public void spendKey(Player p) {
        for (int i = 0; i < p.getInventory().getContainerSize(); i++) {
            ItemStack stack = p.getInventory().getItem(i);
            if (stack.is(getKeyItem())) {
                stack.shrink(1);
                return;
            }
        }
    }
}
