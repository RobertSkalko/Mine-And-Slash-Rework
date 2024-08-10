package com.robertx22.mine_and_slash.database.data.currency.loc_reqs.gems;

import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.mine_and_slash.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.network.chat.MutableComponent;

public class NoDuplicateSocketsReq extends BaseLocRequirement {
    @Override
    public MutableComponent getText() {
        return Words.NoDuplicateSockets.locName();
    }

    @Override
    public boolean isAllowed(LocReqContext ctx) {

        if (ctx.Currency.getItem() instanceof GemItem) {

            GemItem gitem = (GemItem) ctx.Currency.getItem();

            Gem gem = gitem.getGem();

            GearItemData gear = (GearItemData) ctx.data;

            return gear.sockets.getSocketed().stream()
                    .noneMatch(x -> x.getGem() != null && x.getGem().gem_type
                            .equals(gem.gem_type));

        }

        return false;
    }
}
