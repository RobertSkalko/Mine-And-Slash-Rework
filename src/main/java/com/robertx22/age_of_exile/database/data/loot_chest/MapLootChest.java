package com.robertx22.age_of_exile.database.data.loot_chest;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChest;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestData;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.MapBlueprint;
import com.robertx22.age_of_exile.loot.req.DropRequirement;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MapLootChest extends LootChest {

    @Override
    public ItemStack generateOne(LootChestData data) {
        MapBlueprint b = new MapBlueprint(LootInfo.ofLevel(data.lvl));
        b.rarity.set(data.getRarity());
        return b.createStack();
    }

    @Override
    public int minLevelDrop() {
        return ServerContainer.get().MIN_LEVEL_MAP_DROPS.get();
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
        return "map";
    }

    @Override
    public int Weight() {
        return (int) (ServerContainer.get().MAP_DROPRATE.get() * 100);
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
