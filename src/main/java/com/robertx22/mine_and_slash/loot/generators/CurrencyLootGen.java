package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class CurrencyLootGen extends BaseLootGen<ItemBlueprint> {

    public CurrencyLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ServerContainer.get().CURRENCY_DROPRATE.get().floatValue();
        return chance;
    }

    @Override
    public LootType lootType() {
        return LootType.Currency;
    }

    @Override
    public boolean condition() {
        return info.level > 5;
    }

    @Override
    public ItemStack generateOne() {

        ExileCurrency currency = ExileDB.Currency()
                .getFilterWrapped(x -> x.drop_req.canDropInLeague(info.league, info.level))
                .random();

        return currency.getItem().getDefaultInstance();

    }

}
