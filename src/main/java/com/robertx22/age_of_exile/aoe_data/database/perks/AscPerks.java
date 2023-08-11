package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class AscPerks implements ExileRegistryInit {
    public static String TEST = "test";

    @Override
    public void registerAll() {

        PerkBuilder.stat(TEST, new OptScaleExactStat(100, DatapackStats.STR, ModType.FLAT));


    }
}
