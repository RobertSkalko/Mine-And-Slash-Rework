package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.JewelBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class JewelLootGen extends BaseLootGen<MapBlueprint> {

    public JewelLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ServerContainer.get().JEWEL_DROPRATE.get().floatValue();

        return chance;

    }

    @Override
    public LootType lootType() {
        return LootType.Jewel;
    }

    @Override
    public boolean condition() {
        return this.info.level > 5;
    }

  
    @Override
    public ItemStack generateOne() {
        JewelBlueprint b = new JewelBlueprint(info);

        return b.createStack();

    }

}
