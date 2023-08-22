package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.generator.RoomType;

public class NatureRoomAdder extends BaseRoomAdder {

    public NatureRoomAdder() {
        super();
    }


    @Override
    public void addAllRooms() {

        add("trader", RoomType.CURVED_HALLWAY);
        add("0", RoomType.CURVED_HALLWAY);

        add("boss_evoker", RoomType.END);
        add("mythic_mob_house", RoomType.END);

        add("simple", RoomType.ENTRANCE);

        add("simple4", RoomType.TRIPLE_HALLWAY);
        add("simple5", RoomType.TRIPLE_HALLWAY);

        add("hard_to_find_mobs", RoomType.STRAIGHT_HALLWAY);
        add("tent_treasure", RoomType.STRAIGHT_HALLWAY);

        add("simple2", RoomType.FOUR_WAY);

    }
}

