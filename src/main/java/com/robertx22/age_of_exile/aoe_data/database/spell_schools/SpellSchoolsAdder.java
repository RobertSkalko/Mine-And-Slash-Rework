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

    public static String ELEMENTALIST = "elementalist";

    public enum AscRow {
        ONE(0),
        TWO(2),
        THREE(4),
        FOUR(6);

        public int y;

        AscRow(int y) {
            this.y = y;
        }
    }

    @Override
    public void registerAll() {
        // todo

        SchoolBuilder.of(ELEMENTALIST, "Elementalist")

                .addFor(AscRow.ONE, AscPerks.FIRE_NOVICE, AscPerks.COLD_NOVICE, AscPerks.LIGHTNING_NOVICE)
                .addFor(AscRow.TWO, AscPerks.FIRE_APPRENTICE, AscPerks.COLD_APPRENTICE, AscPerks.LIGHTNING_APPRENTICE)
                .addFor(AscRow.THREE, AscPerks.FIRE_EXPERT, AscPerks.COLD_EXPERT, AscPerks.LIGHTNING_EXPERT)
                .addFor(AscRow.FOUR, AscPerks.FIRE_MASTER, AscPerks.COLD_MASTER, AscPerks.LIGHTNING_MASTER)

                .build();


        /*
        SchoolBuilder.of(DIVINE, "Divine")

                .addFor(AscRow.ONE, AscPerks.TEST, AscPerks.TEST, AscPerks.TEST)


                .build();


         */

    }
}
