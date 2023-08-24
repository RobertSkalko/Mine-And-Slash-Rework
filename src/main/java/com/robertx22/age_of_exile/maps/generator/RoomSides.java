package com.robertx22.age_of_exile.maps.generator;

import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class RoomSides {

    public RoomSide SOUTH;
    public RoomSide NORTH;
    public RoomSide EAST;
    public RoomSide WEST;


    public List<Direction> getDoorSides() {

        List<Direction> list = new ArrayList<>();

        if (SOUTH == RoomSide.DOOR) {
            list.add(Direction.SOUTH);
        }

        if (NORTH == RoomSide.DOOR) {
            list.add(Direction.NORTH);
        }

        if (EAST == RoomSide.DOOR) {
            list.add(Direction.EAST);
        }

        if (WEST == RoomSide.DOOR) {
            list.add(Direction.WEST);
        }
        return list;
    }

    public RoomSides(RoomSide SOUTH, RoomSide NORTH, RoomSide EAST, RoomSide WEST) {
        this.SOUTH = SOUTH;
        this.NORTH = NORTH;
        this.EAST = EAST;
        this.WEST = WEST;
    }


    public RoomSide getSideOfDirection(Direction dir) {
        if (dir == Direction.NORTH) {
            return NORTH;
        } else if (dir == Direction.SOUTH) {
            return SOUTH;
        } else if (dir == Direction.EAST) {
            return EAST;
        } else if (dir == Direction.WEST) {
            return WEST;
        }
        return null;
    }

    public boolean matches(RoomSides data) {
        return SOUTH.canBeLinked(data.SOUTH) && NORTH.canBeLinked(data.NORTH) && EAST.canBeLinked(data.EAST) && WEST.canBeLinked(data.WEST);
    }

    public String getFirstLineDebug() {
        String s = "";
        s += "X;" + NORTH.debugString + ";X";
        return s;
    }

    public String getSecondString() {
        String s = "";
        s += WEST.debugString + ";O;" + EAST.debugString;
        return s;

    }

    public String getThirdString() {
        String s = "";
        s += "X;" + SOUTH.debugString + ";X";
        return s;

    }

    public String getDebugString() {
        String s = "";
        s += "X" + NORTH.debugString + "X" + "\n";
        s += WEST.debugString + "O" + EAST.debugString + "\n";
        s += "X" + SOUTH.debugString + "X" + "\n";
        return s;

    }
}