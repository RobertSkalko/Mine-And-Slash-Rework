package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.GearRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class IsUnderQualityReq extends GearRequirement {

    public static Data UNDER_20 = new Data(20);
    public static Data UNDER_21 = new Data(21);

    public Data data;

    public static record Data(int max_quality) {
    }

    public IsUnderQualityReq(String id, Data data) {
        super(ItemReqSers.IS_UNDER_QUALITY, id);
        this.data = data;
    }

    @Override
    public Class<?> getClassForSerialization() {
        return IsUnderQualityReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.max_quality);
    }

    @Override
    public String locDescForLangFile() {
        return "Must be under %1$s Quality";
    }

    @Override
    public boolean isGearValid(ExileStack stack) {
        return stack.CUSTOM.getOrCreate().data.get(CustomItemData.KEYS.QUALITY) < data.max_quality;
    }
}
