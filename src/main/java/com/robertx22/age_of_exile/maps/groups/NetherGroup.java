package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class NetherGroup extends RoomGroup {

    public NetherGroup() {
        super("nether", 750);
        this.canBeMainTheme = true;
        this.canSpawnFireMobs = true;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.ALL);
    }
}
