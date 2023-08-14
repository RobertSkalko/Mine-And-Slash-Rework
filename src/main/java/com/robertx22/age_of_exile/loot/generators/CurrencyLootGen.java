package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
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

        Currency currency = ExileDB.CurrencyItems()
                .getFilterWrapped(x -> true)
                .random();

        return currency.getCurrencyItem().getDefaultInstance();

    }

}
