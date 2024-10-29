package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;
import net.minecraft.network.chat.MutableComponent;

public class CorruptGearItemMod extends GearModification {
    public Data data;

    public static record Data(boolean adds_affixes) {
    }

    public CorruptGearItemMod(String id, Data data) {
        super(ItemModificationSers.CORRUPT_GEAR, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.get(StackKeys.GEAR).edit(gear -> {
            if (data.adds_affixes) {
                // todo will this work
                var chaos = ExileDB.ChaosStats().getFilterWrapped(x -> x.for_item_rarities.contains(gear.rar)).random();
                chaos.applyToGear(stack);
            } else {
                stack.get(StackKeys.CUSTOM).edit(x -> x.data.set(CustomItemData.KEYS.CORRUPT, true));
                stack.get(StackKeys.POTENTIAL).edit(e -> e.potential = 0);
            }
        });
    }

    @Override
    public Class<?> getClassForSerialization() {
        return CorruptGearItemMod.class;
    }


    @Override
    public OutcomeType getOutcomeType() {
        return this.data.adds_affixes ? OutcomeType.GOOD : OutcomeType.BAD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams();
    }

    @Override
    public String locDescForLangFile() {
        String t = "Corrupts the Item, making it unmodifiable";
        if (data.adds_affixes) {
            t += " and adds a Random amount of Corruption Affixes";
        }
        return t;
    }
}
