package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.perks.AscPerks;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {

    public static String DIVINE = "divine";
    public static String HUNTING = "hunting";
    public static String FIRE = "fire";
    public static String NATURE = "nature";
    public static String WATER = "water";
    public static String UNHOLY = "unholy";

    @Override
    public void registerAll() {

        SchoolBuilder.of(DIVINE, "Divine")

                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)


                .build();

        SchoolBuilder.of(HUNTING, "Hunting")

                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)

                .build();

        SchoolBuilder.of(NATURE, "Nature")

                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)


                .build();

        SchoolBuilder.of(FIRE, "Fire")
                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)

                .build();

        SchoolBuilder.of(WATER, "Water")
                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)

                .build();

        SchoolBuilder.of(UNHOLY, "Unholy")
                .addFor(0, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)

                .build();

    }
}
