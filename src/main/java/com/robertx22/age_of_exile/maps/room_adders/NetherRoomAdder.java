package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.generator.RoomType;

public class NetherRoomAdder extends BaseRoomAdder {

    public NetherRoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("climb_to_treasure", RoomType.CURVED_HALLWAY);
        add("the_witch", RoomType.CURVED_HALLWAY);

        add("boss2", RoomType.END);
        add("nether0", RoomType.END);
        add("hidden_lever_boss", RoomType.END);

        add("0", RoomType.ENTRANCE);
        add("hidden_chest_lava", RoomType.ENTRANCE);

        add("blazes", RoomType.FOUR_WAY);
        add("wart_farm", RoomType.FOUR_WAY);
        add("tower_of_doom", RoomType.FOUR_WAY);

        add("lava_detour", RoomType.STRAIGHT_HALLWAY);
        add("skeletons0", RoomType.STRAIGHT_HALLWAY);

        add("dangerous_lava", RoomType.TRIPLE_HALLWAY);
        add("easy0", RoomType.TRIPLE_HALLWAY);

    }
}

