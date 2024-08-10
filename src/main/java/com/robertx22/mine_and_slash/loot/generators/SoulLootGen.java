package com.robertx22.mine_and_slash.loot.generators;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.stat_soul.StatSoulData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.LootType;
import net.minecraft.world.item.ItemStack;

public class SoulLootGen extends BaseLootGen<GearBlueprint> {

    public SoulLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {
        float chance = (float) ServerContainer.get().SOUl_DROPRATE.get().floatValue();
        return chance;
    }

    @Override
    public LootType lootType() {
        return LootType.Gear;
    }

    public static ItemStack createSoulBasedOnGear(GearBlueprint blueprint) {
        GearItemData gear = blueprint.createData();

        StatSoulData soul = new StatSoulData();

        soul.rar = gear.rar;
        soul.slot = gear.GetBaseGearType().gear_slot;
        soul.tier = gear.getTier();

        if (gear.isUnique()) {
            soul.uniq = gear.data.get(GearItemData.KEYS.UNIQUE_ID);
        }

        ItemStack stack = soul.toStack();
        return stack;
    }

    @Override
    public ItemStack generateOne() {
        GearBlueprint blueprint = new GearBlueprint(info);
        return createSoulBasedOnGear(blueprint);
    }

}
