package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class TestRoomAdder extends BaseRoomAdder {
    @Override
    public void addAllRooms() {

        add("test", RoomType.CURVED_HALLWAY);
        add("test", RoomType.END);
        add("test", RoomType.ENTRANCE);
        add("test", RoomType.FOUR_WAY);
        add("test", RoomType.STRAIGHT_HALLWAY);
        add("test", RoomType.TRIPLE_HALLWAY);
    }
}
