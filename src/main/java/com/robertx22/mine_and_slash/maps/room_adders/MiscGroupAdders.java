package com.robertx22.mine_and_slash.maps.room_adders;

import com.robertx22.mine_and_slash.maps.generator.RoomType;

public class MiscGroupAdders extends BaseRoomAdder {

    public MiscGroupAdders() {
        super();
    }

    @Override
    public void addAllRooms() {
      

        add("simple_prismarine", RoomType.FOUR_WAY);
        add("prismarine", RoomType.ENTRANCE);
        add("obsidian_lava0", RoomType.TRIPLE_HALLWAY);
        add("easy_sandstone_puzzle", RoomType.CURVED_HALLWAY);
        add("infested_cellar", RoomType.STRAIGHT_HALLWAY);


    }
}



