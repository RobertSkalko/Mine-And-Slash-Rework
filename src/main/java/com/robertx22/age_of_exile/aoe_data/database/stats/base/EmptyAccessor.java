package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.library_of_exile.registry.IGUID;

public class EmptyAccessor extends AutoHashClass implements IGUID {
    public static EmptyAccessor INSTANCE = new EmptyAccessor();

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String GUID() {
        return "empty";
    }
}
