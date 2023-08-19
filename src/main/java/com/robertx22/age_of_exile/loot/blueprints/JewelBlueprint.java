package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class JewelBlueprint extends ItemBlueprint {


    public GearRarityPart rarity = new GearRarityPart(this);

    public JewelBlueprint(LootInfo info) {
        super(info);
        this.rarity.chanceForHigherRarity = 50;
    }

    @Override
    public ItemStack generate() {


        JewelItemData data = createData();

        ItemStack stack = data.getItem().getDefaultInstance();

        StackSaving.JEWEL.saveTo(stack, data);

        return stack;

    }

    public JewelItemData createData() {
        JewelItemData data = new JewelItemData();

        PlayStyle style = RandomUtils.randomFromList(Arrays.stream(PlayStyle.values()).toList());

        data.rar = this.rarity.get().GUID();

        data.style = style.id;

        data.lvl = info.level;

        data.generateAffixes();

        return data;
    }


}
