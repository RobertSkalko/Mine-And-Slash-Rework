package com.robertx22.age_of_exile.mechanics.harvest.loot;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HarvestGreenChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {

        Currency currency = ExileDB.CurrencyItems().getFilterWrapped(x -> x.getDropReq().canDropInLeague(LeagueMechanics.HARVEST)).random();

        return new ItemStack(currency.getCurrencyItem(), 1 + data.getRarity().item_tier);
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build();
    }

    @Override
    public Item getKey() {
        return HarvestItems.GREEN_KEY.get();
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return HarvestItems.GREEN_CHEST.get();
    }

    @Override
    public String GUID() {
        return "harvest_green_chest";
    }

    @Override
    public int Weight() {
        return 1000;
    }

}
