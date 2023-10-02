package com.robertx22.age_of_exile.aoe_data.database.spell_schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SpellSchoolsAdder implements ExileRegistryInit {


    @Override
    public void registerAll() {
        // todo


        // fire + ice + golems
        // dps, summoner, self sustain
        SchoolBuilder.of("sorcerer", "Sorcerer")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();


        // summons + chaos + curses
        // dps, summoner, debuffer
        SchoolBuilder.of("warlock", "Warlock")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();

        // songs + heals
        // dps, buffer, healer
        SchoolBuilder.of("minstrel", "Minstrel")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();

        // archer
        // dps, pet
        SchoolBuilder.of("hunter", "Hunter")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();

        // lightning + totems
        // dps, healer
        SchoolBuilder.of("shaman", "Shaman")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();

        // uh warrior stuff
        // tank dps
        SchoolBuilder.of("warrior", "Warrior")
                .add(FireSpells.METEOR, new PointData(0, 0))

                .build();


    }
}
