package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class ModifyUniqueStatsItemMod extends GearModification {


    public Data data;

    public static record Data(int num) {
    }

    public ModifyUniqueStatsItemMod(String id, Data data) {
        super(ItemModificationSers.MODIFY_UNIQUE_STATS, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.GEAR.edit(gear -> {
            gear.uniqueStats.increaseAllBy(gear, data.num);
        });
    }

    @Override
    public Class<?> getClassForSerialization() {
        return ModifyUniqueStatsItemMod.class;
    }


    @Override
    public OutcomeType getOutcomeType() {
        return data.num > 0 ? OutcomeType.GOOD : OutcomeType.BAD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        return this.getDescParams(data.num);
    }

    @Override
    public String locDescForLangFile() {

        String term = "Adds";
        if (data.num < 0) {
            term = "Reduces";
        }

        return term + " %1$s Percent to Unique Stats";
    }
}
