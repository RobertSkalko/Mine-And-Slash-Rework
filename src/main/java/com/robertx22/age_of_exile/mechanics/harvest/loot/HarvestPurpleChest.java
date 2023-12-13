package com.robertx22.age_of_exile.mechanics.harvest.loot;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.JewelBlueprint;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HarvestPurpleChest extends LootChest {

    // todo needs something unique

    @Override
    public ItemStack generateOne(LootChestData data) {
        JewelBlueprint b = new JewelBlueprint(LootInfo.ofLevel(data.lvl));
        b.rarity.set(data.getRarity());
        return b.createStack();
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build();
    }

    @Override
    public Item getKey() {
        return HarvestItems.PURPLE_KEY.get();
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return HarvestItems.PURPLE_CHEST.get();
    }

    @Override
    public String GUID() {
        return "harvest_purple_chest";
    }

    @Override
    public int Weight() {
        return 1000;
    }

}