package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class MossyBrickRoomAdder extends BaseRoomAdder {

    public MossyBrickRoomAdder() {
        super(RoomGroup.MOSSY_BRICK);
    }

    @Override
    public void addAllRooms() {

        add("2", RoomType.CURVED_HALLWAY);
        add("parkour0", RoomType.CURVED_HALLWAY);

        add("6", RoomType.END);
        add("7", RoomType.END);
        add("boss0", RoomType.END).setBoss();

        add("0", RoomType.FOUR_WAY);
        add("double_level_chest", RoomType.FOUR_WAY);

        add("4", RoomType.STRAIGHT_HALLWAY);
        add("5", RoomType.STRAIGHT_HALLWAY);
        add("8", RoomType.STRAIGHT_HALLWAY);
        add("parkour_maze", RoomType.STRAIGHT_HALLWAY);
        add("safe_room", RoomType.STRAIGHT_HALLWAY);
        add("underwater_chest", RoomType.STRAIGHT_HALLWAY);

        add("1", RoomType.TRIPLE_HALLWAY);
        add("3", RoomType.TRIPLE_HALLWAY);
        add("camo_chest", RoomType.TRIPLE_HALLWAY);

    }
}

