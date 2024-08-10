package com.robertx22.mine_and_slash.maps.generator;


public enum RoomSide {
    DOOR("O"),
    BLOCKED("Z"),
    UNBUILT("E");

    RoomSide(String debug) {
        this.debugString = debug;
    }

    public String debugString;

    public boolean canBeLinked(RoomSide side) {
        return this == UNBUILT || side == UNBUILT || this == side;

    }

}