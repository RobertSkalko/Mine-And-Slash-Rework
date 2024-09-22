package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.GearRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class MustHaveAffixesReq extends GearRequirement {

    public MustHaveAffixesReq(String id) {
        super(ItemReqSers.HAS_AFFIXES, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return MustHaveAffixesReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        return "Must have affixes";
    }

    @Override
    public boolean isGearValid(ExileStack stack) {
        var gear = stack.GEAR.get();
        return gear.affixes != null && gear.affixes.getNumberOfAffixes() > 0;
    }
}
