package com.robertx22.age_of_exile.database.data.prophecy;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.blueprints.ItemBlueprint;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public abstract class ProphecyStart implements ExileRegistry<ProphecyStart> {
    public static ProphecyStart SERIALIZER = new ProphecyStart() {

        @Override
        public int Weight() {
            return 0;
        }

        @Override
        public String GUID() {
            return null;
        }

        @Override
        public ItemBlueprint create(int lvl, int tier) {
            return null;
        }
    };

    public abstract ItemBlueprint create(int lvl, int tier);

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.PROPHECY_START;
    }

}
