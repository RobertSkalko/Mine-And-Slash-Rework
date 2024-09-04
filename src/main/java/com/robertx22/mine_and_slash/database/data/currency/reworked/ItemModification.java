package com.robertx22.mine_and_slash.database.data.currency.reworked;

import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.mine_and_slash.uncommon.interfaces.IBaseAutoLoc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IDesc;
import net.minecraft.world.item.ItemStack;

public abstract class ItemModification implements ExileRegistry<ItemModification>, IDesc {

    public abstract void apply(ItemStack stack);

  
    @Override
    public ExileRegistryType getExileRegistryType() {
        return null; // todo
    }

    @Override
    public IBaseAutoLoc.AutoLocGroup getLocGroup() {
        return IBaseAutoLoc.AutoLocGroup.Currency_Items;
    }

}
