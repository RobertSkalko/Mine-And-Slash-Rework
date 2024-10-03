package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;

public abstract class MapModification extends ItemModification {

    public MapModification(String serializer, String id) {
        super(serializer, id);
    }

    public abstract void modifyMap(ExileStack map);

    @Override
    public void applyINTERNAL(ExileStack stack) {
        var map = stack.get(StackKeys.MAP).get();

        if (map != null) {
            modifyMap(stack);
        }
    }

}
