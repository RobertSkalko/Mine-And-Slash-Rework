package com.robertx22.age_of_exile.database.data.currency.base;

import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

// todo
public abstract class JewelCurrency extends Currency {

    @Override
    public ItemStack internalModifyMethod(LocReqContext ctx, ItemStack stack, ItemStack currency) {
        JewelItemData gem = StackSaving.JEWEL.loadFrom(stack);
        return modify(ctx, stack, gem);
    }

    @Override
    public boolean canItemBeModified(LocReqContext context) {

        JewelItemData data = StackSaving.JEWEL.loadFrom(context.stack);
        if (data == null) {
            return false;
        }
        return super.canItemBeModified(context) && canBeModified(data);
    }

    public abstract boolean canBeModified(JewelItemData data);

    public abstract ItemStack modify(LocReqContext ctx, ItemStack stack, JewelItemData data);

    @Override
    public List<BaseLocRequirement> requirements() {
        return Arrays.asList();
    }
}
