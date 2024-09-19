package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.Arrays;

public class Omens {

    public static void init() {

        new Omen("shadows", "Omen of Shadows", 0.2F,
                Arrays.asList(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow).mod(5, 20))
        ).addToSerializables();

    }
}
