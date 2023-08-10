package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class SteampunkRoomAdder extends BaseRoomAdder {

    public SteampunkRoomAdder() {
        super(RoomGroup.STEAMPUNK);
    }

    @Override
    public void addAllRooms() {

        add("royal_plus_sewer", RoomType.END);

        add("lava_pit", RoomType.FOUR_WAY);
        add("slow_cooker_parkour", RoomType.FOUR_WAY);

        add("0", RoomType.ENTRANCE);
        add("royal", RoomType.ENTRANCE);

        add("no_puzzle_trick", RoomType.TRIPLE_HALLWAY);
        add("royal_dwarves", RoomType.TRIPLE_HALLWAY);

        add("flower_lever_combo", RoomType.STRAIGHT_HALLWAY);

    }
}