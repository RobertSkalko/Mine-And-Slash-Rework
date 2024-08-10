package com.robertx22.mine_and_slash.maps.generator;


import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.maps.DungeonRoom;
import com.robertx22.mine_and_slash.maps.dungeon_reg.Dungeon;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;

import java.util.Objects;

public class BuiltRoom {

    private String dungeon;
    public RoomRotation data;
    public DungeonRoom room;

    public BuiltRoom(Dungeon dun, RoomRotation data, DungeonRoom room) {
        this.data = data;
        this.room = room;
        this.dungeon = dun.GUID();

    }

    public Dungeon getDungeon() {
        return ExileDB.Dungeons().get(dungeon);
    }

    public ResourceLocation getStructure() {
        return room.loc;
    }

    public static BuiltRoom getBarrier() {
        DungeonRoom barrier = new DungeonRoom(Dungeon.SERIALIZER, "", RoomType.END); // todo what
        barrier.isBarrier = true;
        barrier.loc = new ResourceLocation(SlashRef.MODID, "dun/barrier");
        RoomRotation rot = new RoomRotation(RoomType.END, new RoomSides(RoomSide.BLOCKED, RoomSide.BLOCKED, RoomSide.BLOCKED, RoomSide.BLOCKED), Rotation.NONE);
        BuiltRoom built = new BuiltRoom(Dungeon.SERIALIZER, rot, barrier);
        return built;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuiltRoom builtRoom = (BuiltRoom) o;
        return this.hashCode() == builtRoom.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(room.toString(), data.rotation, data.type);
    }
}