package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.LootChestBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
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
        LootChestBlueprint b = new LootChestBlueprint(info);
        return b.createStack();

    }

}