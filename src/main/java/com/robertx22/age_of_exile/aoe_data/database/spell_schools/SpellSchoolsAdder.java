package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.perks.asc.ElementalistPerks;
import com.robertx22.age_of_exile.aoe_data.database.perks.asc.PaladinPerks;
import com.robertx22.age_of_exile.aoe_data.database.perks.asc.SummonerPerks;
import com.robertx22.age_of_exile.aoe_data.database.perks.asc.WarlockPerks;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {


    public static String ELEMENTALIST = "elementalist";
    public static String WARLOCK = "warlock";
    public static String SUMMONER = "summoner";
    public static String PALADIN = "paladin";

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
                .addFor(AscRow.ONE, ElementalistPerks.FIRE_NOVICE, ElementalistPerks.COLD_NOVICE, ElementalistPerks.LIGHTNING_NOVICE)
                .addFor(AscRow.TWO, ElementalistPerks.FIRE_APPRENTICE, ElementalistPerks.COLD_APPRENTICE, ElementalistPerks.LIGHTNING_APPRENTICE)
                .addFor(AscRow.THREE, ElementalistPerks.FIRE_EXPERT, ElementalistPerks.COLD_EXPERT, ElementalistPerks.LIGHTNING_EXPERT)
                .addFor(AscRow.FOUR, ElementalistPerks.FIRE_MASTER, ElementalistPerks.COLD_MASTER, ElementalistPerks.LIGHTNING_MASTER)
                .build();

        SchoolBuilder.of(WARLOCK, "Warlock")
                .addFor(AscRow.ONE, WarlockPerks.POISON_1, WarlockPerks.CURSE_1, WarlockPerks.CHAOS_1)
                .addFor(AscRow.TWO, WarlockPerks.POISON_2, WarlockPerks.CURSE_2, WarlockPerks.CHAOS_2)
                .addFor(AscRow.THREE, WarlockPerks.POISON_3, WarlockPerks.CURSE_3, WarlockPerks.CHAOS_3)
                .addFor(AscRow.FOUR, WarlockPerks.POISON_4, WarlockPerks.CURSE_4, WarlockPerks.CHAOS_4)
                .build();

        SchoolBuilder.of(PALADIN, "Paladin")
                .addFor(AscRow.ONE, PaladinPerks.PALADIN_1, PaladinPerks.GUARDIAN_1, PaladinPerks.WARRIOR_1)
                .addFor(AscRow.TWO, PaladinPerks.PALADIN_2, PaladinPerks.GUARDIAN_2, PaladinPerks.WARRIOR_2)
                .addFor(AscRow.THREE, PaladinPerks.PALADIN_3, PaladinPerks.GUARDIAN_3, PaladinPerks.WARRIOR_3)
                .addFor(AscRow.FOUR, PaladinPerks.PALADIN_4, PaladinPerks.GUARDIAN_4, PaladinPerks.WARRIOR_4)
                .build();

        SchoolBuilder.of(SUMMONER, "Summoner")
                .addFor(AscRow.ONE, SummonerPerks.SUMMON_1, SummonerPerks.GOLEM_1, SummonerPerks.TOTEM_1)
                .addFor(AscRow.TWO, SummonerPerks.SUMMON_2, SummonerPerks.GOLEM_2, SummonerPerks.TOTEM_2)
                .addFor(AscRow.THREE, SummonerPerks.SUMMON_3, SummonerPerks.GOLEM_3, SummonerPerks.TOTEM_3)
                .addFor(AscRow.FOUR, SummonerPerks.SUMMON_4, SummonerPerks.GOLEM_4, SummonerPerks.TOTEM_4)
                .build();


    }
}
