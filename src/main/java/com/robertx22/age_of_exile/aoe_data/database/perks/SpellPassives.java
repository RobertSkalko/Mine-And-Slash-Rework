package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellPassives implements ExileRegistryInit {

    public static String BURN_CHANCE = "burn_chance";

    @Override
    public void registerAll() {

        PerkBuilder.passive(BURN_CHANCE, 8, new OptScaleExactStat(2, new AilmentChance(Ailments.BURN)));

    }
}
