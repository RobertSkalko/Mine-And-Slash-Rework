package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.GearRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class HasInfusionReq extends GearRequirement {

    public HasInfusionReq(String id) {
        super(ItemReqSers.HAS_INFUSION, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return HasInfusionReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Must have Infusion";
    }

    @Override
    public boolean isGearValid(ExileStack stack) {
        var gear = stack.get(StackKeys.GEAR).get();
        return gear.ench != null && !gear.ench.isEmpty();
    }
}
