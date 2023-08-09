package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class SewersRoomAdder extends BaseRoomAdder {

    public SewersRoomAdder() {
        super(RoomGroup.SEWERS);
    }

    @Override
    public void addAllRooms() {

        add("sewers", RoomType.FOUR_WAY);
        add("sewers_flow", RoomType.FOUR_WAY);
        add("sewers", RoomType.ENTRANCE);
        add("sewers_treasure", RoomType.END).weight(300);
        add("sewers_puzzle_easy", RoomType.TRIPLE_HALLWAY);
        add("sewers", RoomType.CURVED_HALLWAY);
        add("sewers", RoomType.STRAIGHT_HALLWAY);

    }
}


