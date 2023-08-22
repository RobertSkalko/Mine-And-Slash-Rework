package com.robertx22.age_of_exile.maps.dungeon_reg;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.age_of_exile.maps.generator.RoomType;
import com.robertx22.age_of_exile.maps.room_adders.BaseRoomAdder;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Dungeon implements IAutoGson<Dungeon>, JsonExileRegistry<Dungeon> {

    public static Dungeon SERIALIZER = new Dungeon();

    public String id = "";
    public int weight = 1000;

    public boolean can_be_main = true;

    public String mob_list = ""; // todo

    public List<String> entrances = new ArrayList<>();
    public List<String> four_ways = new ArrayList<>();
    public List<String> straight_hallways = new ArrayList<>();
    public List<String> curved_hallways = new ArrayList<>();
    public List<String> triple_hallway = new ArrayList<>();
    public List<String> ends = new ArrayList<>();

    public List<String> other_tileset = new ArrayList<>();

    public float other_tileset_chance = 5;

    public boolean allowsOtherTilesets() {
        return !other_tileset.isEmpty();
    }

    public List<Dungeon> getPossibleOtherTilesets() {

        var list = other_tileset.stream().map(x -> ExileDB.Dungeons().get(x)).collect(Collectors.toList());

        return list;
    }


    public Dungeon getFallbackGroup(Random rand) {
        if (!this.allowsOtherTilesets()) {
            return ExileDB.Dungeons().get("misc");
        } else {
            return RandomUtils.weightedRandom(getPossibleOtherTilesets(), rand.nextDouble());
        }
    }

    private transient List<DungeonRoom> rooms = new ArrayList<>();


    public List<DungeonRoom> getRooms() {
        if (rooms.isEmpty()) {
            addRooms(RoomType.ENTRANCE, entrances);
            addRooms(RoomType.FOUR_WAY, four_ways);
            addRooms(RoomType.STRAIGHT_HALLWAY, straight_hallways);
            addRooms(RoomType.CURVED_HALLWAY, curved_hallways);
            addRooms(RoomType.TRIPLE_HALLWAY, triple_hallway);
            addRooms(RoomType.END, ends);
        }
        return rooms;
    }

    public List<DungeonRoom> getRoomsOfType(RoomType type) {
        return getRooms()
                .stream()
                .filter(x -> x.type.equals(type)).collect(Collectors.toList());

    }

    public List<String> getRoomList(RoomType type) {

        if (type == RoomType.END) {
            return ends;
        }
        if (type == RoomType.ENTRANCE) {
            return entrances;
        }
        if (type == RoomType.FOUR_WAY) {
            return four_ways;
        }
        if (type == RoomType.CURVED_HALLWAY) {
            return curved_hallways;
        }
        if (type == RoomType.STRAIGHT_HALLWAY) {
            return straight_hallways;
        }
        if (type == RoomType.TRIPLE_HALLWAY) {
            return triple_hallway;
        }

        return Arrays.asList();
    }


    private void addRooms(RoomType type, List<String> list) {
        for (String room : list) {
            DungeonRoom b = new DungeonRoom(this, room, type);
            this.rooms.add(b);
        }
    }

    public final boolean hasRoomFor(RoomType type) {
        return getRooms()
                .stream()
                .anyMatch(x -> x.type.equals(type));

    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.DUNGEON;
    }

    @Override
    public Class<Dungeon> getClassForSerialization() {
        return Dungeon.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    public static class Builder {

        Dungeon dungeon = new Dungeon();

        public static Builder of(String id, BaseRoomAdder adder) {
            Builder b = new Builder();
            b.dungeon.id = id;
            adder.addRoomsToDungeon(b.dungeon);
            return b;
        }

        public Builder weight(int w) {
            this.dungeon.weight = w;
            return this;
        }

        public Builder setIsOnlyAsAdditionalRooms() {
            this.dungeon.can_be_main = false;
            return this;
        }

        public Dungeon build() {
            dungeon.addToSerializables();
            return dungeon;
        }


    }
}
