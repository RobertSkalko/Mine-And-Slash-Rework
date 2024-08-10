package com.robertx22.mine_and_slash.aoe_data.database.dim_configs;

import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class DimConfigs implements ExileRegistryInit {

    @Override
    public void registerAll() {

        DimensionConfig.Overworld().addToSerializables();
        DimensionConfig.Nether().addToSerializables();
        DimensionConfig.End().addToSerializables();
        DimensionConfig.DefaultExtra().addToSerializables();

    }
}
