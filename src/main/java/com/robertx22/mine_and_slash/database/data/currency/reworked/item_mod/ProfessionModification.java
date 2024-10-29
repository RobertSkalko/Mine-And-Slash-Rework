package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;

public abstract class ProfessionModification extends ItemModification {

    public ProfessionModification(String serializer, String id) {
        super(serializer, id);
    }

    public abstract void modifyProfessionItem(ExileStack data);

    @Override
    public void applyINTERNAL(ExileStack stack) {
        var data = stack.get(StackKeys.TOOL).get();

        if (data != null) {
            modifyProfessionItem(stack);
        }
    }

}
