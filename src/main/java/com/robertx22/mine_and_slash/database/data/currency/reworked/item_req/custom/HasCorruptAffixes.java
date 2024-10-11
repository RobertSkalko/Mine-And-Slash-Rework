package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class HasCorruptAffixes extends ItemRequirement {

    public HasCorruptAffixes(String id) {
        super(ItemReqSers.HAS_CORRUPTION_AFFIXES, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return HasCorruptAffixes.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public boolean isValid(ExileStack s) {

        if (s.get(StackKeys.GEAR).has()) {
            return !s.get(StackKeys.GEAR).get().affixes.cor.isEmpty();
        }
        if (s.get(StackKeys.JEWEL).has()) {
            return !s.get(StackKeys.JEWEL).get().cor.isEmpty();
        }

        return false;
    }

    @Override
    public String locDescForLangFile() {
        return "Must have Corruption affixes";
    }


}
