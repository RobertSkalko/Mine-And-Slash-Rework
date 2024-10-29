package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationResult;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Items;

public class DestroyItemMod extends ItemModification {

    public DestroyItemMod(String id) {
        super(ItemModificationSers.DESTROY_ITEM, id);
    }


    @Override
    public Class<?> getClassForSerialization() {
        return DestroyItemMod.class;
    }


    @Override
    public void applyINTERNAL(ExileStack stack, ItemModificationResult r) {

    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.BAD;
    }

    public ItemModificationResult applyMod(ExileStack stack) {
        stack.setStack(Items.COAL.getDefaultInstance());
        return null;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "DESTROYS the Item!";
    }
}
