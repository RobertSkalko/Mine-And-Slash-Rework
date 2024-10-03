package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req;

import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.itemstack.StackKeys;

public abstract class MapRequirement extends ItemRequirement {
    public MapRequirement(String serializer, String id) {
        super(serializer, id);
    }

    public abstract boolean isMapValid(ExileStack stack);

    @Override
    public boolean isValid(ExileStack obj) {
        if (obj.get(StackKeys.MAP).has()) {
            return isMapValid(obj);
        }
        return false;
    }
}
