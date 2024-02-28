package com.robertx22.age_of_exile.aoe_data.database.prophecies;

import com.robertx22.age_of_exile.database.data.prophecy.starts.GearProphecy;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ProphecyStarts implements ExileRegistryInit {
    @Override
    public void registerAll() {
        new GearProphecy().registerToExileRegistry();
    }
}
