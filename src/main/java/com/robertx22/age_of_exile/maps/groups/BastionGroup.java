package com.robertx22.age_of_exile.maps.groups;

import com.robertx22.age_of_exile.maps.RoomGroup;

import java.util.Arrays;
import java.util.List;

public class BastionGroup extends RoomGroup {

    public BastionGroup() {
        super("bastion", 2000);
        this.canSpawnFireMobs = true;
    } // todo
    
    @Override
    public List<RoomGroup> possibleOtherTypes() {
        return Arrays.asList(RoomGroup.BASTION);
    }
}

