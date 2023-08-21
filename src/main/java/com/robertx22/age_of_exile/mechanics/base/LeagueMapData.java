package com.robertx22.age_of_exile.mechanics.base;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class LeagueMapData {

    private String structure = "";

    public int kills = 0;

    public Long spawn_pos = 0L;

    public String getStructure(LeagueMechanic m) {
        if (structure.isEmpty()) {
            structure = RandomUtils.weightedRandom(m.getPieces().list).folder;
        }
        return structure;
    }

}
