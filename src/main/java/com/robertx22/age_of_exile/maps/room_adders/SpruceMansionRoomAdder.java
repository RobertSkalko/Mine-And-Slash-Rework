package com.robertx22.age_of_exile.maps.room_adders;


import com.robertx22.age_of_exile.maps.generator.RoomType;

public class SpruceMansionRoomAdder extends BaseRoomAdder {

    public SpruceMansionRoomAdder() {
        super();
    }

    @Override
    public void addAllRooms() {

        add("2", RoomType.CURVED_HALLWAY);

        add("boss_bedroom", RoomType.END);
        add("boss_chest_behind_throne", RoomType.END);
        add("boss0", RoomType.END);
        add("parkour_hidden_slime", RoomType.END);
        add("slime_parkour_hidden", RoomType.END);
        add("under_snow_room", RoomType.END);

        add("0", RoomType.ENTRANCE);

        add("ice_pillar", RoomType.FOUR_WAY);
        add("redstone_ore_secret", RoomType.FOUR_WAY);

        add("1", RoomType.STRAIGHT_HALLWAY);
        add("blocked_but_under_snow", RoomType.STRAIGHT_HALLWAY);
        add("crouching", RoomType.STRAIGHT_HALLWAY);
        add("dangerous_underground", RoomType.STRAIGHT_HALLWAY);
        add("peek_secret_ice", RoomType.STRAIGHT_HALLWAY);
        add("simple3", RoomType.STRAIGHT_HALLWAY);

        add("hidden_trapdoor_room", RoomType.TRIPLE_HALLWAY); // todo remove or set weight to 100
        add("simple", RoomType.TRIPLE_HALLWAY);
        add("2", RoomType.TRIPLE_HALLWAY);


    }
}
