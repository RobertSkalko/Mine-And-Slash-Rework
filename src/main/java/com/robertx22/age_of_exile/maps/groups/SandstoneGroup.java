package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class SandstoneGroup extends RoomGroup {

    public SandstoneGroup() {
        super("sandstone", 800);
        this.canSpawnFireMobs = true;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.ALL);
    }
}
