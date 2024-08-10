package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class GemLootGen extends BaseLootGen<GearBlueprint> {

    public GemLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {
        return (float) (ServerContainer.get().GEM_DROPRATE.get().floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.Gem;
    }

    @Override
    public boolean condition() {
        return !ExileDB.Gems().getFilterWrapped(x -> this.info.level >= x.getReqLevelToDrop()).list.isEmpty();
    }

    @Override
    public ItemStack generateOne() {
        return ExileDB.Gems().getFilterWrapped(x -> this.info.level >= x.getReqLevelToDrop()).random().getItem().getDefaultInstance();
    }

}
