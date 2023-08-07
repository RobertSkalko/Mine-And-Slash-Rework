package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public abstract class GearOutcome implements IWeighted {

    public enum OutcomeType {
        GOOD, BAD;
    }

    public abstract Words getName();

    public abstract OutcomeType getOutcomeType();

    public abstract ItemStack modify(LocReqContext ctx, GearItemData gear, ItemStack stack);


    public MutableComponent getTooltip() {
        return getName().locName().append(", Base Weight: " + Weight());
    }

}
