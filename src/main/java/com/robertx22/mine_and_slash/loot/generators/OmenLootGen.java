package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.omen.OmenBlueprint;
import com.robertx22.mine_and_slash.database.data.omen.OmenPart;
import com.robertx22.mine_and_slash.loot.LootInfo;
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
    public boolean condition() {
        return !OmenPart.DroppableOmens(info.level).list.isEmpty();
    }

    @Override
    protected ItemStack generateOne() {
        OmenBlueprint blueprint = new OmenBlueprint(info);
        return blueprint.createStack();
    }
}
