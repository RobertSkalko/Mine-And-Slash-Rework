package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.GearRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class CanAddSocketsReq extends GearRequirement {

    public CanAddSocketsReq(String id) {
        super(ItemReqSers.CAN_ADD_SOCKETS, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return CanAddSocketsReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Must have additional socket slots";
    }

    @Override
    public boolean isGearValid(ExileStack stack) {
        var gear = stack.get(StackKeys.GEAR).get();
        return gear.sockets.canAddSocket(gear).can;
    }
}
