package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class IceTempleRoomAdder extends BaseRoomAdder {

    public IceTempleRoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("caverns", RoomType.CURVED_HALLWAY);
        add("ice_windows", RoomType.CURVED_HALLWAY);
        add("ice_rooms", RoomType.CURVED_HALLWAY);
        add("prayer_room", RoomType.CURVED_HALLWAY);

        add("boss_pool", RoomType.END);
        add("evoker_boss", RoomType.END);
        add("prison", RoomType.END);

        add("pit", RoomType.ENTRANCE);

        add("strongmobs", RoomType.FOUR_WAY);
        add("waterfall_stairway", RoomType.FOUR_WAY);
        add("ziggurat", RoomType.FOUR_WAY);

        add("arch", RoomType.STRAIGHT_HALLWAY);
        add("broken_bridge", RoomType.STRAIGHT_HALLWAY);

        add("altar", RoomType.TRIPLE_HALLWAY);
        add("fort_trader", RoomType.TRIPLE_HALLWAY);
        add("pool0", RoomType.TRIPLE_HALLWAY);

    }
}



