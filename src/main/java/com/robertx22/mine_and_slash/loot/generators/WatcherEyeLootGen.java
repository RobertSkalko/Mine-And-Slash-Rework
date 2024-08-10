package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.content.ubers.UberBossTier;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.JewelBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.MapBlueprint;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.ItemStack;

public class WatcherEyeLootGen extends BaseLootGen<MapBlueprint> {

    public WatcherEyeLootGen(LootInfo info) {
        super(info);
    }

    // todo add a command for this
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
        JewelBlueprint b = new JewelBlueprint(info);

        var map = Load.mapAt(info.world, info.pos);

        var tier = UberBossTier.map.get(map.map.uber_tier);


        b.isEye = true;
        b.auraAffixes = tier.watcherEyeAffixes;
        b.level.set(info.level);

        return b.createStack();

    }

}
