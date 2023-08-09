package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class AllRoomAdder extends BaseRoomAdder {


    public AllRoomAdder() {
        super(RoomGroup.ALL);
    }

    @Override
    public void addAllRooms() {

        add("vaults", RoomType.END);
    }
}
