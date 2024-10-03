package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class IsNotCorruptedReq extends ItemRequirement {

    public IsNotCorruptedReq(String id) {
        super(ItemReqSers.IS_NOT_CORRUPTED, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return IsNotCorruptedReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public boolean isValid(ExileStack stack) {
        if (stack.get(StackKeys.CUSTOM).hasAndTrue(x -> x.isCorrupted())) {
            return false;
        }
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return "Must not be corrupted";
    }

}
