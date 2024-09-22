package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.mine_and_slash.itemstack.ExileStack;

public abstract class JewelModification extends ItemModification {

    public JewelModification(String serializer, String id) {
        super(serializer, id);
    }

    public abstract void modifyJewel(ExileStack data);

    @Override
    public void applyINTERNAL(ExileStack stack) {
        var data = stack.JEWEL.get();

        if (data != null) {
            modifyJewel(stack);
        }
    }

}
