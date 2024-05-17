package com.robertx22.age_of_exile.maps.dungeon_reg;

import com.robertx22.age_of_exile.maps.room_adders.*;
import com.robertx22.age_of_exile.tags.all.DungeonTags;

public class Dungeons {

    public static void init() {

        Dungeon.Builder.of("cement", "Cemetery", new CementeryAdder()).weight(2000).build();

        Dungeon.Builder.of("warped", "Warped Forest", new WarpedRoomAdder()).tags(DungeonTags.FOREST).weight(2000).build();
        Dungeon.Builder.of("wn", "Nature's End", new WideNatureRoomAdder()).tags(DungeonTags.FOREST).weight(2000).build();
        Dungeon.Builder.of("bastion", "The Bastion", new BastionRoomAdder()).weight(2000).build();
        Dungeon.Builder.of("sewer2", "Slime Sewers", new Sewer2RoomAdder()).weight(2000).build();

        Dungeon.Builder.of("pyramid", "The Pyramid", new PyramidRoomAdder()).weight(500).build();


        Dungeon.Builder.of("nature", "Natural", new NatureRoomAdder()).tags(DungeonTags.FOREST).weight(300).build();
        Dungeon.Builder.of("brick", "Brickhouse", new BrickRoomAdder()).setIsOnlyAsAdditionalRooms().weight(100).build();
        Dungeon.Builder.of("it", "Ice Temple", new IceTempleRoomAdder()).weight(1000).build();
        Dungeon.Builder.of("mossy_brick", "Mossy Temple", new MossyBrickRoomAdder()).weight(750).build();
        Dungeon.Builder.of("nether", "The Nether", new NetherRoomAdder()).weight(750).build();
        Dungeon.Builder.of("mine", "The Mineshaft", new MineRoomAdder()).weight(600).build();
        Dungeon.Builder.of("sandstone", "Sandstone", new SandstoneRoomAdder()).weight(800).build();

        Dungeon.Builder.of("sewers", "Sewers", new SewersRoomAdder()).weight(200).build();
        Dungeon.Builder.of("spruce_mansion", "Spruce Mansion", new SpruceMansionRoomAdder()).weight(700).build();
        Dungeon.Builder.of("stone_brick", "Stone Brick", new StoneBrickRoomAdder()).weight(1000).build();
        Dungeon.Builder.of("tent", "Giant Tents", new TentRoomAdder()).weight(600).build();

        Dungeon.Builder.of("steampunk", "Steampunk", new SteampunkRoomAdder()).weight(50).build();

        Dungeon.Builder.of("misc", "Random", new MiscGroupAdders()).setIsOnlyAsAdditionalRooms().weight(0).build();

    }
}
