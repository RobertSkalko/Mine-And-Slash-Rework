package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class MossyBrickGroup extends RoomGroup {

    public MossyBrickGroup() {
        super("mossy_brick", 750);
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.STONE_BRICK, RoomGroup.SEWERS, RoomGroup.ALL);
    }
}

