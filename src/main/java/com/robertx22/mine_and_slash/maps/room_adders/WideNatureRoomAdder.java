package com.robertx22.mine_and_slash.maps.room_adders;


import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class WideNatureRoomAdder extends BaseRoomAdder {

    public WideNatureRoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("pond", RoomType.CURVED_HALLWAY);
        add("cave0", RoomType.END);
        add("roses", RoomType.ENTRANCE);
        add("crossroad0", RoomType.FOUR_WAY);
        add("tunnel", RoomType.STRAIGHT_HALLWAY);
        add("webhouse", RoomType.TRIPLE_HALLWAY);

        add("basement_boss", RoomType.CURVED_HALLWAY);
        add("hanging_platforms", RoomType.CURVED_HALLWAY);
        add("zombie_boss_shrooms", RoomType.END);
        add("camp_site", RoomType.ENTRANCE);
        add("big_tree", RoomType.FOUR_WAY);
        add("ambush_cave", RoomType.STRAIGHT_HALLWAY);
        add("winding_road", RoomType.STRAIGHT_HALLWAY);
        add("webhouse", RoomType.TRIPLE_HALLWAY);
        add("log", RoomType.TRIPLE_HALLWAY);
        add("spider_cave", RoomType.TRIPLE_HALLWAY);

    }
}


