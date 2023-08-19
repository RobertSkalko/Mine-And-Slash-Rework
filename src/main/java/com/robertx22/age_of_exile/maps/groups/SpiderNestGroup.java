package com.robertx22.age_of_exile.maps.groups;

import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class SpiderNestGroup extends RoomGroup {

    public SpiderNestGroup() {
        super("spider_nest", 0); // todo needs fixing, uses signs with wrong terms
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList();
    }
}

