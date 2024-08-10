package com.robertx22.mine_and_slash.aoe_data.database.stats.base;

import com.robertx22.library_of_exile.registry.IGUID;

public abstract class AutoHashClass implements IGUID {

    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
