package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class DoNothingItemMod extends ItemModification {

    public DoNothingItemMod(String id) {
        super(ItemModificationSers.DO_NOTHING, id);
    }


    @Override
    public Class<?> getClassForSerialization() {
        return DoNothingItemMod.class;
    }


    @Override
    public void applyINTERNAL(ExileStack stack) {

    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.NEUTRAL;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Does Nothing";
    }
}
