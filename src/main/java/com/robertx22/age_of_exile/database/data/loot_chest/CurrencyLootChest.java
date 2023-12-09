package com.robertx22.age_of_exile.database.data.loot_chest;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CurrencyLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {


        Currency currency = ExileDB.CurrencyItems()
                .getFilterWrapped(x -> x.getDropReq().canDropInLeague(LeagueMechanics.NONE))
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

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Lootboxes;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".chest_type." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return GUID().substring(0, 1).toUpperCase() + GUID().substring(1);
    }


}
