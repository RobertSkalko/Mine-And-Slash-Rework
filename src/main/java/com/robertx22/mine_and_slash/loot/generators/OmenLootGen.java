package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.omen.OmenBlueprint;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class OmenLootGen extends BaseLootGen<OmenBlueprint> {
    public OmenLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        return ServerContainer.get().OMEN_DROPRATE.get().floatValue();
    }

    @Override
    public LootType lootType() {
        return LootType.Omen;
    }

    @Override
    protected ItemStack generateOne() {
        MapBlueprint blueprint = new MapBlueprint(info);
        return blueprint.createStack();
    }
}
