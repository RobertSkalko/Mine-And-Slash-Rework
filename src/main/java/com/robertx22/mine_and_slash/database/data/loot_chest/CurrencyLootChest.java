package com.robertx22.mine_and_slash.database.data.loot_chest;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.currency.base.Currency;
import com.robertx22.mine_and_slash.database.data.league.LeagueMechanics;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChest;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CurrencyLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {


        Currency currency = ExileDB.CurrencyItems()
                .getFilterWrapped(x -> x.getDropReq().canDropInLeague(LeagueMechanics.NONE, data.lvl))
                .random();


        return new ItemStack(currency.getCurrencyItem(), 1 + data.getRarity().item_tier);
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().build();
    }

    @Override
    public Item getKey() {
        return null;
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return SlashItems.CURRENCY_CHEST.get();
    }

    @Override
    public String GUID() {
        return "currency";
    }

    @Override
    public int Weight() {
        return (int) (ServerContainer.get().CURRENCY_DROPRATE.get() * 100);
    }


}
