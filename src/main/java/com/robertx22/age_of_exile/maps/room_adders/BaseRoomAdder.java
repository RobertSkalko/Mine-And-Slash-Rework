package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.dungeon_reg.Dungeon;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public abstract class BaseRoomAdder {

    Dungeon dun;

    public BaseRoomAdder() {

    }

    public void add(String id, RoomType type) {
        dun.getRoomList(type).add(id);
    }

    public abstract void addAllRooms();

    public final void addRoomsToDungeon(Dungeon dun) {
        this.dun = dun;
        addAllRooms();
    }

}
