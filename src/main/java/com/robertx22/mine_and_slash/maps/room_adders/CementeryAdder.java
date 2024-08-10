package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class CementeryAdder extends BaseRoomAdder {
    @Override
    public void addAllRooms() {

        add("cemetry_corner01", RoomType.CURVED_HALLWAY);
        add("cemetry_corner02", RoomType.CURVED_HALLWAY);
        add("cemetry_corner03", RoomType.CURVED_HALLWAY);
        add("cemetry_corner04", RoomType.CURVED_HALLWAY);

        add("boss_room01", RoomType.END);
        add("boss_room02", RoomType.END);


        add("cemetry_entrance01", RoomType.ENTRANCE);
        add("cemetry_entrance02", RoomType.ENTRANCE);

        add("cemetry_fourway01", RoomType.FOUR_WAY);
        add("cemetry_fourway02", RoomType.FOUR_WAY);
        add("cemetry_fourway03", RoomType.FOUR_WAY);
        add("cemetry_fourway04", RoomType.FOUR_WAY);


        add("cemetry_straight01", RoomType.STRAIGHT_HALLWAY);
        add("cemetry_straight02", RoomType.STRAIGHT_HALLWAY);
        add("cemetry_straight03", RoomType.STRAIGHT_HALLWAY);
        add("cemetry_straight04", RoomType.STRAIGHT_HALLWAY);

        add("cemetry_tsec01", RoomType.TRIPLE_HALLWAY);
        add("cemetry_tsec02", RoomType.TRIPLE_HALLWAY);
        add("cemetry_tsec03", RoomType.TRIPLE_HALLWAY);
        add("cemetry_tsec04", RoomType.TRIPLE_HALLWAY);

    }
}
