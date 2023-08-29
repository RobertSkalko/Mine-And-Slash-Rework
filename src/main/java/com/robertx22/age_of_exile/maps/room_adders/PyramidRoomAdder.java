package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.generator.RoomType;

public class PyramidRoomAdder extends BaseRoomAdder {

    @Override
    public void addAllRooms() {

        add("atumrun_corner01", RoomType.CURVED_HALLWAY);
        add("atumrun_corner02", RoomType.CURVED_HALLWAY);
        add("atumrun_corner03", RoomType.CURVED_HALLWAY);
        add("atumrun_corner04", RoomType.CURVED_HALLWAY);
        add("atumrun_corner05", RoomType.CURVED_HALLWAY);
        add("atumrun_corner06", RoomType.CURVED_HALLWAY);

        add("atumrun_bossroom01", RoomType.END);
        add("atumrun_bossroom02", RoomType.END);
        add("atumrun_bossroom03", RoomType.END);
        add("atumrun_bossroom04", RoomType.END);
        add("atumrun_bossroom05", RoomType.END);
        add("atumrun_bossroom06", RoomType.END);

        add("atumrun_entrance", RoomType.ENTRANCE);

        add("atumrun_cross01", RoomType.FOUR_WAY);
        add("atumrun_cross02", RoomType.FOUR_WAY);
        add("atumrun_cross03", RoomType.FOUR_WAY);
        add("atumrun_cross04", RoomType.FOUR_WAY);
        add("atumrun_cross05", RoomType.FOUR_WAY);
        add("atumrun_cross06", RoomType.FOUR_WAY);

        add("atumrun_straight01", RoomType.STRAIGHT_HALLWAY);
        add("atumrun_straight02", RoomType.STRAIGHT_HALLWAY);
        add("atumrun_straight03", RoomType.STRAIGHT_HALLWAY);
        add("atumrun_straight04", RoomType.STRAIGHT_HALLWAY);
        add("atumrun_straight05", RoomType.STRAIGHT_HALLWAY);
        add("atumrun_straight06", RoomType.STRAIGHT_HALLWAY);

        add("atumrun_tsection01", RoomType.TRIPLE_HALLWAY);
        add("atumrun_tsection02", RoomType.TRIPLE_HALLWAY);
        add("atumrun_tsection03", RoomType.TRIPLE_HALLWAY);
        add("atumrun_tsection04", RoomType.TRIPLE_HALLWAY);
        add("atumrun_tsection05", RoomType.TRIPLE_HALLWAY);
        add("atumrun_tsection06", RoomType.TRIPLE_HALLWAY);

    }
}
