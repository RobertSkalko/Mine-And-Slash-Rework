package com.robertx22.age_of_exile.aoe_data.database.dim_configs;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class DimConfigs implements ExileRegistryInit {

    @Override
    public void registerAll() {

        DimensionConfig.Overworld().addToSerializables();
        DimensionConfig.Nether().addToSerializables();
        DimensionConfig.End().addToSerializables();


    }
}
