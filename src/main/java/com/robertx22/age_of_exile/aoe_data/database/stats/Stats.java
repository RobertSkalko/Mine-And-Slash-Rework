package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.DatapackStatBuilder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;


// todo split this into multiple classes
public class Stats implements ExileRegistryInit {

    public static void loadClass() {

        OffenseStats.init();
        DefenseStats.init();
        EffectStats.init();
        ProcStats.init();
        ResourceStats.init();
        SpellChangeStats.init();

    }


    @Override
    public void registerAll() {


        DatapackStatBuilder.STATS_TO_ADD_TO_SERIALIZATION.forEach(x -> x.addToSerializables());
    }
}
