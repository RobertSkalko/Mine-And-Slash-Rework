package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;

public class LeagueData {

    public static LeagueData EMPTY = new LeagueData();

    public Long spawn_pos = 0L;

    public int remainingSpawns = 0;

    // for any custom data like chance to drop currency, favor gain per mob etc
    // for a start it will just be remaining spawns
    public MapHolder map = new MapHolder();

}
