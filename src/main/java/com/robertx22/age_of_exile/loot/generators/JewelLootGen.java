package com.robertx22.age_of_exile.loot.generators;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.MapBlueprint;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

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
    public boolean hasLevelDistancePunishment() {
        return false;
    }

    @Override
    public ItemStack generateOne() {

        JewelItemData data = new JewelItemData();

        PlayStyle style = RandomUtils.randomFromList(Arrays.stream(PlayStyle.values()).toList());

        data.rar = ExileDB.GearRarities().random().GUID();

        data.style = style.id;

        data.lvl = info.level;

        data.generateAffixes();

        ItemStack stack = data.getItem().getDefaultInstance();

        StackSaving.JEWEL.saveTo(stack, data);

        return stack;
    }

}
