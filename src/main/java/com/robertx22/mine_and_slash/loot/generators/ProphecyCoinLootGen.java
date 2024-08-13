package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.profession.LeveledItem;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;
import com.robertx22.mine_and_slash.uncommon.coins.Coin;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.LevelUtils;
import net.minecraft.world.item.ItemStack;

public class ProphecyCoinLootGen extends BaseLootGen<ItemBlueprint> {

    public ProphecyCoinLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ServerContainer.get().PROPHECY_COIN_DROPRATE.get().floatValue();
        chance *= 1F + (info.map_tier / 25F);
        chance *= Load.player(info.player).prophecy.affixesTaken.size();
        return chance;
    }

    @Override
    public boolean chanceIsModified() {
        return true;
    }

    @Override
    public LootType lootType() {
        return LootType.ProphecyCoin;
    }

    @Override
    public boolean condition() {
        return info.isMapWorld && info.map != null && info.player != null && !Load.player(info.player).prophecy.affixesTaken.isEmpty();
    }

    @Override
    public boolean hasLevelDistancePunishment() {
        return true;
    }

    @Override
    public ItemStack generateOne() {
        ItemStack s = new ItemStack(Coin.PROPHECY.getItem());
        LeveledItem.setTier(s, LevelUtils.levelToTier(info.level));
        return s;
    }

}