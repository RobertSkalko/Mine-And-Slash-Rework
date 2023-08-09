package com.robertx22.age_of_exile.maps.groups;

import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class IceTempleGroup extends RoomGroup {

    public IceTempleGroup() {
        super("it", 1000);
        this.canSpawnFireMobs = false;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.ALL);
    }
}