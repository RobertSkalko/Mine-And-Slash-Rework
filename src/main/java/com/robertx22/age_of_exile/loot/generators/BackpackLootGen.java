package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.capability.player.BackpackItemData;
import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

public class BackpackLootGen extends BaseLootGen<ItemBlueprint> {

    public BackpackLootGen(LootInfo info) {
        super(info);
    }

    @Override
    public float baseDropChance() {

        float chance = (float) ServerContainer.get().BACKPACK_DROPRATE.get().floatValue();
        return chance;
    }

    @Override
    public LootType lootType() {
        return LootType.Backpack;
    }

    @Override
    public boolean condition() {
        return true;
    }


    @Override
    public ItemStack generateOne() {

        BackpackItemData data = new BackpackItemData();

        data.rar = ExileDB.GearRarities().random().GUID();


        int min = (int) ((data.getRarity().skill_gem_percents.min / 100F) * Backpacks.MAX_SIZE);
        int max = (int) ((data.getRarity().skill_gem_percents.max / 100F) * Backpacks.MAX_SIZE);

        for (Backpacks.BackpackType type : Backpacks.BackpackType.values()) {

            data.map.put(type, RandomUtils.RandomRange(min, max));
        }

        ItemStack stack = new ItemStack(SlashItems.BACKPACK.get());

        StackSaving.BACKPACK.saveTo(stack, data);

        return stack;

    }

}
