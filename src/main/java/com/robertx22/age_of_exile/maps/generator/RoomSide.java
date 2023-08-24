package com.robertx22.age_of_exile.maps.generator;


public enum RoomSide {
    DOOR("O"), BLOCKED("Z"), NONE("E");

    RoomSide(String debug) {
        this.debugString = debug;
    }

    public String debugString;

    public boolean canBeLinked(RoomSide side) {
        return this == NONE || side == NONE || this == side;
    }

    public boolean isLinked(RoomSide side) {
        return this == side;
    }

}