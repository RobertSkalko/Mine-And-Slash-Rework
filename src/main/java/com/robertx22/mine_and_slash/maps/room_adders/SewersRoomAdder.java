package com.robertx22.mine_and_slash.maps.room_adders;


import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class SewersRoomAdder extends BaseRoomAdder {

    public SewersRoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("sewers", RoomType.FOUR_WAY);
        add("sewers_flow", RoomType.FOUR_WAY);
        add("sewers", RoomType.ENTRANCE);
        add("sewers_treasure", RoomType.END);
        add("sewers_puzzle_easy", RoomType.TRIPLE_HALLWAY);
        add("sewers", RoomType.CURVED_HALLWAY);
        add("sewers", RoomType.STRAIGHT_HALLWAY);

    }
}


