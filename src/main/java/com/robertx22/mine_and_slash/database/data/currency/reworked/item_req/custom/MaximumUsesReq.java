package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.saveclasses.item_classes.rework.DataKey;
import net.minecraft.network.chat.MutableComponent;

public class MaximumUsesReq extends ItemRequirement {


    public Data data;

    public static record Data(String use_id, int max_uses) {
    }

    public MaximumUsesReq(String id, Data data) {
        super(ItemReqSers.MAX_USES, id);
        this.data = data;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return MaximumUsesReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.max_uses);
    }


    @Override
    public String locDescForLangFile() {
        return "Maximum %1$s uses";
    }

    @Override
    public boolean isValid(ExileStack stack) {
        var uses = stack.CUSTOM.getOrCreate().data.get(new DataKey.IntKey(data.use_id));
        return uses < data.max_uses;
    }
}
