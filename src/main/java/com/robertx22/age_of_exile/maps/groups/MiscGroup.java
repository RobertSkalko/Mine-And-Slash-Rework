package com.robertx22.age_of_exile.maps.groups;


import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class MiscGroup extends RoomGroup {

    public MiscGroup() {
        super("misc", 25);
        this.canBeMainTheme = false;
    }

    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList();
    }

}