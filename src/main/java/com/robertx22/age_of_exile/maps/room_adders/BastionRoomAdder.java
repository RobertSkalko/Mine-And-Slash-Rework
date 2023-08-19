package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class BastionRoomAdder extends BaseRoomAdder {

    public BastionRoomAdder() {
        super(RoomGroup.BASTION);
    }

    @Override
    public void addAllRooms() {

        add("barracks", RoomType.CURVED_HALLWAY);
        add("basic0", RoomType.CURVED_HALLWAY);
        add("parkour", RoomType.CURVED_HALLWAY);
        add("rings", RoomType.CURVED_HALLWAY);
        add("winding", RoomType.CURVED_HALLWAY);

        add("basalt_boss", RoomType.END);
        add("boss", RoomType.END);
        add("prison", RoomType.END);
        add("tree_farm", RoomType.END);

        add("lava_fountains", RoomType.ENTRANCE);
        add("nether_wastes", RoomType.ENTRANCE);


        add("4pillars", RoomType.FOUR_WAY);
        add("basic3", RoomType.FOUR_WAY);
        add("lava_basins", RoomType.FOUR_WAY);
        add("pillar1", RoomType.FOUR_WAY);
        add("treasure_room", RoomType.FOUR_WAY);

        add("basic1", RoomType.STRAIGHT_HALLWAY);
        add("bridge", RoomType.STRAIGHT_HALLWAY);
        add("furnaces", RoomType.STRAIGHT_HALLWAY);
        add("ores", RoomType.STRAIGHT_HALLWAY);
        add("pillar0", RoomType.STRAIGHT_HALLWAY);

        add("basalt0", RoomType.TRIPLE_HALLWAY);
        add("basalt1", RoomType.TRIPLE_HALLWAY);
        add("underbridge", RoomType.TRIPLE_HALLWAY);
        add("woodcutter", RoomType.TRIPLE_HALLWAY);

    }
}


