package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.stream.Collectors;

public class IsNoneReq extends ItemRequirement {

    public Data data;

    public static record Data(List<String> requirements) {
    }

    transient String desc;

    public IsNoneReq(String id, Data data, String desc) {
        super(ItemReqSers.IS_NONE, id);
        this.data = data;
        this.desc = desc;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return IsNoneReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public boolean isValid(ExileStack obj) {
        var all = data.requirements.stream().map(x -> ExileDB.ItemReq().get(x)).collect(Collectors.toList());
        for (ItemRequirement req : all) {
            if (req.isValid(obj)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return desc;
    }

}
