package com.robertx22.age_of_exile.maps.groups;

import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class WarpedGroup extends RoomGroup {

    public WarpedGroup() {
        super("warped", 2000);
        this.canSpawnFireMobs = true;
        this.netherParticles = true;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList();
    }
}

