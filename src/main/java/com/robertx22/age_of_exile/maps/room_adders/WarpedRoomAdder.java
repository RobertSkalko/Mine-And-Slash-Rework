package com.robertx22.age_of_exile.maps.room_adders;

import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.maps.generator.RoomType;

public class WarpedRoomAdder extends BaseRoomAdder {

    public WarpedRoomAdder() {
        super(RoomGroup.WARPED);
    }

    @Override
    public void addAllRooms() {

        
        add("basic", RoomType.CURVED_HALLWAY);
        add("bastion", RoomType.CURVED_HALLWAY);
        add("blocked", RoomType.CURVED_HALLWAY);
        add("cottage", RoomType.CURVED_HALLWAY);

        add("boss", RoomType.END);
        add("crimson", RoomType.END);
        add("overgrown_fortress", RoomType.END);
        add("parkour", RoomType.END);

        add("basic", RoomType.ENTRANCE);
        add("crimson", RoomType.ENTRANCE);

        add("campsite", RoomType.FOUR_WAY);
        add("crossroads", RoomType.FOUR_WAY);
        add("gazeebo", RoomType.FOUR_WAY);
        add("portal", RoomType.FOUR_WAY);

        add("basic", RoomType.STRAIGHT_HALLWAY);
        add("bridge", RoomType.STRAIGHT_HALLWAY);
        add("dig", RoomType.STRAIGHT_HALLWAY);
        add("fortress", RoomType.STRAIGHT_HALLWAY);

        add("abandoned_house", RoomType.TRIPLE_HALLWAY);
        add("overgrown_lab", RoomType.TRIPLE_HALLWAY);
        add("depot", RoomType.TRIPLE_HALLWAY);
        add("lake", RoomType.TRIPLE_HALLWAY);


    }
}


