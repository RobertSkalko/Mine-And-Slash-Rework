package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.mine_and_slash.itemstack.ExileStack;

public abstract class GearModification extends ItemModification {

    public GearModification(String serializer, String id) {
        super(serializer, id);
    }

    public abstract void modifyGear(ExileStack stack);

    @Override
    public void applyINTERNAL(ExileStack stack) {
        var gear = stack.GEAR.get();

        if (gear != null) {
            modifyGear(stack);
        }
    }

}
