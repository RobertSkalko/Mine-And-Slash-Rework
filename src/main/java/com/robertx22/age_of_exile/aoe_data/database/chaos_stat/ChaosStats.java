package com.robertx22.age_of_exile.aoe_data.database.chaos_stat;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.chaos_stats.ChaosStat;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.List;

public class ChaosStats implements ExileRegistryInit {

    @Override
    public void registerAll() {

        new ChaosStat("normal_low", "Upgraded", 5000, 1, Affix.Type.suffix, 0, IRarity.NORMAL_GEAR_RARITIES).addToSerializables();
        new ChaosStat("normal_med", "Elevated", 1000, 2, Affix.Type.suffix, 0, IRarity.NORMAL_GEAR_RARITIES).addToSerializables();
        new ChaosStat("normal_high", "Ascended", 500, 3, Affix.Type.suffix, 0, IRarity.NORMAL_GEAR_RARITIES).addToSerializables();

        new ChaosStat("unique_low", "Upgraded", 5000, 1, Affix.Type.suffix, 1, List.of(IRarity.UNIQUE_ID)).addToSerializables();
        new ChaosStat("unique_med", "Elevated", 1000, 2, Affix.Type.suffix, 2, List.of(IRarity.UNIQUE_ID)).addToSerializables();
        new ChaosStat("unique_high", "Ascended", 250, 3, Affix.Type.suffix, 3, List.of(IRarity.UNIQUE_ID)).addToSerializables();

    }
}
