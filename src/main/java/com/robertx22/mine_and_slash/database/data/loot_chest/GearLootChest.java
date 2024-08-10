package com.robertx22.mine_and_slash.database.data.loot_chest;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChest;
import com.robertx22.mine_and_slash.database.data.loot_chest.base.LootChestData;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.req.DropRequirement;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RarityItems;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GearLootChest extends LootChest {
    @Override
    public ItemStack generateOne(LootChestData data) {
        GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(data.lvl));
        b.rarity.set(data.getRarity());
        return b.createStack();
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
        if (!RarityItems.GEAR_CHESTS.containsKey(data.getRarity())) {
            return RarityItems.GEAR_CHESTS.get(IRarity.COMMON_ID).get();
        }
        return RarityItems.GEAR_CHESTS.get(data.getRarity().GUID()).get();
    }

    @Override
    public String GUID() {
        return "gear";
    }

    @Override
    public int Weight() {
        return (int) (ServerContainer.get().GEAR_DROPRATE.get() * 100);
    }

}
