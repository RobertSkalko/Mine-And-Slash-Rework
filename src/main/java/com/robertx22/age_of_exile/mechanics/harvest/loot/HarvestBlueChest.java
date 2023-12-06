package com.robertx22.age_of_exile.mechanics.harvest.loot;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HarvestBlueChest extends LootChest {

    // todo needs something unique

    @Override
    public ItemStack generateOne(LootChestData data) {
        GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(data.lvl));
        b.rarity.set(data.getRarity());
        return b.createStack();
    }

    @Override
    public DropRequirement getDropReq() {
        return DropRequirement.Builder.of().setOnlyDropsInLeague(LeagueMechanics.HARVEST_ID).build();
    }

    @Override
    public Item getKey() {
        return HarvestItems.BLUE_KEY.get();
    }

    @Override
    public Item getChestItem(LootChestData data) {
        return HarvestItems.BLUE_CHEST.get();
    }

    @Override
    public String GUID() {
        return "harvest_blue_chest";
    }

    @Override
    public int Weight() {
        return 1000;
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
        return "Harvest Blue Chest";
    }
}