package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public abstract class BaseRoomAdder {

    final RoomGroup group;

    public BaseRoomAdder(RoomGroup group) {
        this.group = group;
    }

    public DungeonRoom add(String id, RoomType type) {
        DungeonRoom room = new DungeonRoom(id, type, group);

        RoomList.getRoomsINTERNAL()
                .add(room);

        return room;
    }

    public abstract void addAllRooms();

}
