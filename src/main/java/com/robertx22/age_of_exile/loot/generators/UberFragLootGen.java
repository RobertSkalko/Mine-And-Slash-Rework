package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.content.ubers.UberBossTier;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class UberFragLootGen extends BaseLootGen<ItemBlueprint> {

    public UberFragLootGen(LootInfo info) {
        super(info);

    }

    @Override
    public float baseDropChance() {

        return (float) (ServerContainer.get().UBER_FRAG_DROPRATE.get().floatValue());
    }

    @Override
    public LootType lootType() {
        return LootType.UberFrag;
    }

    @Override
    public boolean condition() {
        var map = Load.mapAt(info.world, info.pos);
        if (map == null || !map.map.isUber()) {
            return false;
        }
        return info.level >= UberBossTier.T1.frag_drop_lvl;
    }

    @Override
    public ItemStack generateOne() {
        var map = Load.mapAt(info.world, info.pos);

        var tier = UberBossTier.getTierForFragmentDrop(info.level);


        var uber = map.map.getUber();

        Item item = SlashItems.UBER_FRAGS.get(uber.getEnum()).get(tier.tier).get();

        ItemStack stack = new ItemStack(item);

        return stack;
    }

}
