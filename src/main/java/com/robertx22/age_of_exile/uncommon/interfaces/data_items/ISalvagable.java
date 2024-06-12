package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.library_of_exile.utils.AllItemStackSavers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ISalvagable {

    List<ItemStack> getSalvageResult(ItemStack stack);

    public ToggleAutoSalvageRarity.SalvageType getSalvageType();

    default String getSalvageConfigurationId() { return null;}

    default boolean isSalvagable() {
        return true;
    }

    static ISalvagable load(ItemStack stack) {

        for (ItemstackDataSaver<? extends ISalvagable> saver : AllItemStackSavers.getAllOfClass(ISalvagable.class)) {
            ISalvagable data = saver.loadFrom(stack);
            if (data != null) {
                return data;
            }
        }
        return null;
    }
}
