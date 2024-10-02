package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.all;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom.MaximumUsesReq;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.item_classes.rework.DataKey;
import net.minecraft.network.chat.MutableComponent;

public class IncrementUsesItemMod extends ItemModification {

    String use_key;

    public IncrementUsesItemMod(String id, MaximumUsesReq.Data data) {
        super(ItemModificationSers.INCREMENT_USES, id);
        this.use_key = data.use_id();
    }

    @Override
    public void applyINTERNAL(ExileStack stack) {
        stack.CUSTOM.edit(gear -> {
            var key = new DataKey.IntKey(use_key);
            int uses = gear.data.get(key) + 1;
            gear.data.set(key, uses);
        });
    }

    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.NEUTRAL;
    }


    @Override
    public Class<?> getClassForSerialization() {
        return IncrementUsesItemMod.class;
    }


    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Increments Uses";
    }
}
