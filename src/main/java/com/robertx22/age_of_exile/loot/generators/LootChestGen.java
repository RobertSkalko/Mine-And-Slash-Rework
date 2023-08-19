package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

public class LootChestGen extends BaseLootGen<GearBlueprint> {

    public LootChestGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().LOOT_CHEST_DROPRATE.get().floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.LootChest;
    }

    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public ItemStack generateOne() {
        LootChestData data = new LootChestData();

        data.num = RandomUtils.RandomRange(4, 8);
        data.lvl = info.level;
        data.rar = ExileDB.GearRarities().getFilterWrapped(x -> x.item_tier > 2).random().GUID();
        data.id = ExileDB.LootChests().getFilterWrapped(x -> x.getDropReq().canDropInLeague(info.league)).random().GUID();

        ItemStack stack = data.getLootChest().getChestItem(data).getDefaultInstance();

        StackSaving.LOOT_CHEST.saveTo(stack, data);

        return stack;
    }

}