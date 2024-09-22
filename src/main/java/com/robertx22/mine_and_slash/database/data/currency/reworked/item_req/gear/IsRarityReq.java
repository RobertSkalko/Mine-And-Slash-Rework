package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.GearRequirement;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class IsRarityReq extends GearRequirement {


    public Data data;

    public static record Data(String rarity) {
    }

    public IsRarityReq(String id, Data data) {
        super(ItemReqSers.IS_RARITY, id);
        this.data = data;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(data.rarity);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return IsRarityReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(getRarity().locName().withStyle(getRarity().textFormatting()));
    }

    @Override
    public String locDescForLangFile() {
        return "Must be %1$s Rarity";
    }

    @Override
    public boolean isGearValid(ExileStack stack) {
        return stack.GEAR.hasAndTrue(x -> x.rar.equals(data.rarity));
    }
}
