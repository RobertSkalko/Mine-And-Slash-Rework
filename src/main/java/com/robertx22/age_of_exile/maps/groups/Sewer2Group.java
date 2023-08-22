package com.robertx22.age_of_exile.maps.groups;

import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class Sewer2Group extends RoomGroup {

    public Sewer2Group() {
        super("sewer2", 2000 + 50000);
        this.canSpawnFireMobs = true;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList();
    }
}
