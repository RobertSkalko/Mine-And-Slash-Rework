package com.robertx22.age_of_exile.maps.dungeon_reg;

import com.robertx22.age_of_exile.maps.room_adders.*;

public class Dungeons {

    public static void init() {

        Dungeon.Builder.of("warped", new WarpedRoomAdder()).weight(2000).build();
        Dungeon.Builder.of("wn", new WideNatureRoomAdder()).weight(2000).build();
        Dungeon.Builder.of("bastion", new BastionRoomAdder()).weight(2000).build();
        Dungeon.Builder.of("sewer2", new Sewer2RoomAdder()).weight(2000).build();
        Dungeon.Builder.of("pyramid", new PyramidRoomAdder()).weight(2000).build();


        Dungeon.Builder.of("nature", new NatureRoomAdder()).weight(300).build();
        Dungeon.Builder.of("brick", new BrickRoomAdder()).setIsOnlyAsAdditionalRooms().weight(100).build();
        Dungeon.Builder.of("it", new IceTempleRoomAdder()).weight(1000).build();
        Dungeon.Builder.of("mossy_brick", new MossyBrickRoomAdder()).weight(750).build();
        Dungeon.Builder.of("nether", new NetherRoomAdder()).weight(750).build();
        Dungeon.Builder.of("mine", new MineRoomAdder()).weight(600).build();
        Dungeon.Builder.of("sandstone", new SandstoneRoomAdder()).weight(800).build();

        Dungeon.Builder.of("sewers", new SewersRoomAdder()).weight(200).build();
        Dungeon.Builder.of("spruce_mansion", new SpruceMansionRoomAdder()).weight(700).build();
        Dungeon.Builder.of("stone_brick", new StoneBrickRoomAdder()).weight(1000).build();
        Dungeon.Builder.of("tent", new TentRoomAdder()).weight(600).build();

        Dungeon.Builder.of("steampunk", new SteampunkRoomAdder()).weight(50).build();

        Dungeon.Builder.of("misc", new MiscGroupAdders()).setIsOnlyAsAdditionalRooms().weight(0).build();

    }
}
