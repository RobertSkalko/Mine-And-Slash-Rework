package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.WatcherEyeBlueprint;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.ItemStack;

public class WatcherEyeLootGen extends BaseLootGen<MapBlueprint> {

    public WatcherEyeLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ServerContainer.get().WATCHER_EYE_DROPRATE.get().floatValue();
        return chance;
    }

    @Override
    public boolean chanceIsModified() {
        return false;
    }

    @Override
    public LootType lootType() {
        return LootType.WatcherEye;
    }

    @Override
    public boolean condition() {
        return info.mobData != null && info.mobData.getMobRarity().GUID().equals(IRarity.UBER);
    }

    @Override
    public boolean hasLevelDistancePunishment() {
        return false;
    }

    @Override
    public ItemStack generateOne() {
        WatcherEyeBlueprint b = new WatcherEyeBlueprint(info);
        return b.createStack();

    }

}
