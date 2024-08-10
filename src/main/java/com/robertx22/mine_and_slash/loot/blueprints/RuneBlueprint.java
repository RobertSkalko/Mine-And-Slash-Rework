package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.database.data.runes.Rune;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.RunePart;
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

            ItemStack stack = new ItemStack(item);

            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }


}