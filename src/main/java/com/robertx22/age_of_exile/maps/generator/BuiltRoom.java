package com.robertx22.age_of_exile.maps.generator;


import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.age_of_exile.maps.RoomGroup;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;

import java.util.Objects;

public class BuiltRoom {

    public RoomRotation data;
    public DungeonRoom room;

    public BuiltRoom(RoomRotation data, DungeonRoom room) {
        this.data = data;
        this.room = room;

    }

    public ResourceLocation getStructure() {
        return room.loc;
    }

    public static BuiltRoom getBarrier() {
        DungeonRoom barrier = new DungeonRoom("", RoomType.END, RoomGroup.MISC);
        barrier.loc = new ResourceLocation(SlashRef.MODID, "dun/barrier");
        RoomRotation rot = new RoomRotation(RoomType.END, new RoomSides(RoomSide.BLOCKED, RoomSide.BLOCKED, RoomSide.BLOCKED, RoomSide.BLOCKED), Rotation.NONE);
        BuiltRoom built = new BuiltRoom(rot, barrier);
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