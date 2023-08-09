package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class NatureGroup extends RoomGroup {

    public NatureGroup() {
        super("nature", 300);
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.ALL);
    }
}
