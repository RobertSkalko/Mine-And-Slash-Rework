package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import net.minecraft.world.item.ItemStack;

public interface IRarityItem {

    GearRarity getItemRarity(ItemStack stack);

}
