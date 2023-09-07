package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import net.minecraft.world.item.ItemStack;

public interface IRarityItem {

    GearRarity getItemRarity(ItemStack stack);

}
