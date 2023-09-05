package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.world.item.ItemStack;

public interface ICommonDataItem<R extends Rarity> extends ISalvagable, ITooltip, IRarity {

    @Override
    default boolean isSalvagable() {
        return true;
    }

    public int getLevel();

    ItemstackDataSaver<? extends ICommonDataItem> getStackSaver();

    void saveToStack(ItemStack stack);

    static ICommonDataItem load(ItemStack stack) {

        for (ItemstackDataSaver<? extends ICommonDataItem> saver : AllItemStackSavers.getAllOfClass(ICommonDataItem.class)) {
            ICommonDataItem data = saver.loadFrom(stack);
            if (data != null) {
                return data;
            }
        }
        return null;
    }

}
