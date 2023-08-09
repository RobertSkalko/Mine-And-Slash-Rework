package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class StoneBrickGroup extends RoomGroup {

    public StoneBrickGroup() {
        super("stone_brick", 1200);
        this.canSpawnFireMobs = true;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.MOSSY_BRICK, RoomGroup.BRICK, RoomGroup.STEAMPUNK, RoomGroup.ALL);
    }

}
