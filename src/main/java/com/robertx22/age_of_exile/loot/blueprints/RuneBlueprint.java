package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.RunePart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RuneBlueprint extends ItemBlueprint {


    public RunePart runePart = new RunePart(this);

    public RuneBlueprint(LootInfo info) {
        super(info);
    }

    // maybe you can socket a rune only in x part instead of any, and it gives big stats, but you can only socket 1 rune per item?
    @Override
    public ItemStack generate() {

        Rune rune = runePart.get();
        Item item = rune.getItem();

        if (rune != null && item != null) {

            // todo do i give runes stats too like gems so they are more interesting?

            ItemStack stack = new ItemStack(item);

            /*
            RuneItemData data = new RuneItemData();

            data.rarity = rarity.get()
                    .Rank();
            data.name = rune.GUID();
            data.level = level.get();

            data.armor = StatModData.NewRandom(data.getRarity(), RandomUtils.weightedRandom(rune.armorStat()));

            data.weapon = StatModData.NewRandom(data.getRarity(), RandomUtils.weightedRandom(rune.weaponStat()));

            data.jewerly = StatModData.NewRandom(data.getRarity(), RandomUtils.weightedRandom(rune.jewerlyStat()));

            Rune.Save(stack, data);


             */
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }


}