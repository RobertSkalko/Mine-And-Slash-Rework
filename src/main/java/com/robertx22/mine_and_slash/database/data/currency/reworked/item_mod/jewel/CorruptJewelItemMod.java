package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.jewel;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.JewelModification;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

// todo probably turn corruption stats into its own component later on
public class CorruptJewelItemMod extends JewelModification {
    public CorruptJewelItemMod(String id) {
        super(ItemModificationSers.JEWEL_CORRUPT, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return CorruptJewelItemMod.class;
    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return locDesc();
    }

    @Override
    public void modifyJewel(ExileStack data) {
        data.JEWEL.edit(x -> x.corrupt());
    }

    @Override
    public String locDescForLangFile() {
        return "Corrupts the Jewel";
    }
}
