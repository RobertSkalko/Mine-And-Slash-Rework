package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class SpiderNestAdder extends BaseRoomAdder {

    // todo
    public SpiderNestAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("corner1", RoomType.CURVED_HALLWAY);
        add("corner2", RoomType.CURVED_HALLWAY);
        add("corner3", RoomType.CURVED_HALLWAY);

        add("bossroom1", RoomType.END);
        add("bossroom2", RoomType.END);
        add("bossroom3", RoomType.END);
        add("finalboss", RoomType.END);

        add("begin", RoomType.ENTRANCE);

        add("fourway1", RoomType.FOUR_WAY);
        add("fourway2", RoomType.FOUR_WAY);

        add("straight1", RoomType.STRAIGHT_HALLWAY);
        add("straight2", RoomType.STRAIGHT_HALLWAY);
        add("straight3", RoomType.STRAIGHT_HALLWAY);

        add("triple1", RoomType.TRIPLE_HALLWAY);
        add("triple1", RoomType.TRIPLE_HALLWAY);

    }
}
