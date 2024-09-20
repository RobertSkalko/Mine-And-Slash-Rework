package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.aoe_data.database.stats.DefenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.Arrays;

// todo at least 10 omens for now!!!
public class Omens {

    public static void init() {


        new Omen("echoes", "Omen of Echoes", 0.3F,
                Arrays.asList(
                        Mana.getInstance().mod(5, 25).percent(),
                        DatapackStats.INT.mod(1, 5).percent()
                )
        ).addToSerializables();

        new Omen("mirrors", "Omen of Mirrors", 0.3F,
                Arrays.asList(
                        DefenseStats.DAMAGE_REDUCTION.get().mod(5, 10)
                )
        ).addToSerializables();

        new Omen("blood", "Omen of Blood", 0.3F,
                Arrays.asList(
                        Health.getInstance().mod(5, 20).percent(),
                        DatapackStats.STR.mod(5, 10).percent()
                )
        ).addToSerializables();

        // elemental

        ele("shadows", "Shadows", Elements.Shadow);
        ele("flames", "Flames", Elements.Fire);
        ele("storms", "Storms", Elements.Nature);
        ele("waves", "Waves", Elements.Cold);
        ele("fangs", "Fangs", Elements.Physical);

    }

    static void ele(String id, String name, Elements ele) {
        new Omen(id, "Omen of " + name, 0.2F,
                Arrays.asList(
                        OffenseStats.ELEMENTAL_DAMAGE.get(ele).mod(5, 20),
                        new MaxElementalResist(ele).mod(1, 5)
                )
        ).addToSerializables();


    }
}
