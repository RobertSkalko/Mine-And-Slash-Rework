package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.jewel;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemReqSers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.JewelRequirement;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class JewelHasAffixesReq extends JewelRequirement {
    public JewelHasAffixesReq(String id) {
        super(ItemReqSers.JEWEL_HAS_AFFIXES, id);
    }

    @Override
    public Class<?> getClassForSerialization() {
        return JewelHasAffixesReq.class;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return locDesc();
    }

    @Override
    public boolean isJewelValid(ExileStack data) {
        return !data.get(StackKeys.JEWEL).get().affixes.isEmpty();
    }

    @Override
    public String locDescForLangFile() {
        return "Jewel Has Affixes";
    }
}
