package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear;

import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.GearModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModificationSers;
import com.robertx22.mine_and_slash.itemstack.CustomItemData;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import net.minecraft.network.chat.MutableComponent;

public class AddQualityItemMod extends GearModification {

    public Data data;

    public static record Data(MinMax add_quality) {
    }

    public AddQualityItemMod(String id, Data data) {
        super(ItemModificationSers.ADD_QUALITY, id);
        this.data = data;
    }

    @Override
    public void modifyGear(ExileStack stack) {
        stack.CUSTOM.edit(gear -> {
            gear.data.set(CustomItemData.KEYS.QUALITY, gear.data.get(CustomItemData.KEYS.QUALITY) + data.add_quality.random());
        });
    }


    @Override
    public OutcomeType getOutcomeType() {
        return OutcomeType.GOOD;
    }

    @Override
    public MutableComponent getDescWithParams() {
        String text = data.add_quality.min + "";
        if (data.add_quality.hasRange()) {
            text = data.add_quality.min + " - " + data.add_quality.max;
        }
        return this.getDescParams(text);
    }


    @Override
    public Class<?> getClassForSerialization() {
        return AddQualityItemMod.class;
    }


    @Override
    public String locDescForLangFile() {
        return "Add %1$s Quality";
    }
}
