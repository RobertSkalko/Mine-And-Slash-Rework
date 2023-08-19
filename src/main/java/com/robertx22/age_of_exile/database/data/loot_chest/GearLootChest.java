package com.robertx22.age_of_exile.database.data.loot_chest;

import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GearLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {
        GearBlueprint b = new GearBlueprint(data.lvl);
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
        return RarityItems.GEAR_CHESTS.get(data.getRarity().GUID()).get();
    }

    @Override
    public String GUID() {
        return "gear";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
