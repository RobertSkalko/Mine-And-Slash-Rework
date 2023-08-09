package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AllGroup extends RoomGroup {

    public AllGroup() {
        super("all", 5);
        this.canBeMainTheme = false;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList();
    }

    @Override
    public RoomGroup getFallbackGroup(Random rand) {
        return RoomGroup.ICE_TEMPLE;
    }
}