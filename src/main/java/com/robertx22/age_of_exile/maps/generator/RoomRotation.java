package com.robertx22.age_of_exile.maps.generator;


import com.robertx22.library_of_exile.registry.IWeighted;
import net.minecraft.world.level.block.Rotation;

public class RoomRotation implements IWeighted {

    public RoomType type;
    public RoomSides sides;
    public Rotation rotation;

    public RoomRotation(RoomType type, RoomSides sides, Rotation rotation) {
        this.type = type;
        this.sides = sides;
        this.rotation = rotation;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}