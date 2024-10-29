package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.map;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.MapRequirement;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class MapHasHigherRarityReq extends MapRequirement {

    public MapHasHigherRarityReq(String id) {
        super(ItemReqSers.MAP_HAS_HIGHER_RAR, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return MapHasHigherRarityReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return locDesc();
    }

    @Override
    public boolean isMapValid(ExileStack stack) {
        var data = stack.get(StackKeys.MAP).get();
        if (!data.getRarity().hasHigherRarity()) {
            return false;
        }
        if (data.lvl >= data.getRarity().getHigherRarity().min_lvl == false) {
            return false;
        }
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return "Map Rarity Must be lower than Mythic and Map Level high enough for the Rarity upgrade";
    }
}
