package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.world.item.ItemStack;

public interface ICommonDataItem<R extends Rarity> extends ISalvagable, ITooltip, IRarity {

    @Override
    default boolean isSalvagable(ExileStack stack) {
        return !stack.get(StackKeys.CUSTOM).getOrCreate().data.get(CustomItemData.KEYS.SALVAGING_DISABLED);
    }

    public int getLevel();

    public default int getSalvageExpReward() {
        GearRarity rar = getRarity();
        return (int) (30 * rar.item_tier_power * getLevel());
    }

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
