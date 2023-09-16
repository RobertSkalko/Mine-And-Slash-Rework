package com.robertx22.age_of_exile.maps.generator;


import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BuiltDungeon {
    public DungeonBuilder b;

    public BuiltDungeon(int size, DungeonBuilder b) {
        this(size, 20);
        this.b = b;
    }

    public BuiltDungeon(int size, int capacity) {
        this.size = size;
        this.capacity = capacity;

        rooms = new BuiltRoom[capacity][capacity];
    }


    int capacity;
    private int size;
    private boolean started = false;
    int amount = 0;
    int ends = 0;
    public int bossRooms = 0;

    private BuiltRoom[][] rooms;

    public BuiltRoom[][] getRooms() {
        return rooms;
    }


    public boolean startFinishing = false;

    public boolean shouldStartFinishing(PointData point) {
        if (startFinishing) {
            return startFinishing;
        }
        if (isTooCloseToGridEdge(point)) {
            return true;
        }
        return amount > size;
    }

    public boolean isTooCloseToGridEdge(PointData p) {
        int too = 3;
        return p.x < too || p.y < too || p.y > capacity - too || p.x > capacity - too;

    }

    public BuiltRoom getRoomForChunk(ChunkPos pos) {
        try {
            ChunkPos start = MapData.getStartChunk(pos.getMiddleBlockPosition(50));
            ChunkPos relative = new ChunkPos(pos.x - start.x, pos.z - start.z);
            return rooms[getMiddle() + relative.x][getMiddle() + relative.z];
        } catch (Exception e) {
        }
        return null;

    }

    public boolean hasRoomForChunk(ChunkPos pos) {
        return getRoomForChunk(pos) != null;
    }

    public BuiltRoom getRoom(int x, int z) {
        if (!isWithinBounds(x, z)) {
            return null;
        }
        return rooms[x][z];
    }

    public boolean isWithinBounds(int x, int z) {
        if (x + 1 > capacity || z + 1 > capacity || x < 0 || z < 0) {
            return false;
        }
        return true;
    }

    public int getMiddle() {
        return rooms.length / 2;
    }

    public PointData getCoordsOfRoomFacing(Direction dir, int x, int z) {
        if (dir == Direction.NORTH) {
            return new PointData(x, z - 1);
        } else if (dir == Direction.SOUTH) {
            return new PointData(x, z + 1);
        } else if (dir == Direction.EAST) {
            return new PointData(x + 1, z);
        } else if (dir == Direction.WEST) {
            return new PointData(x - 1, z);
        }

        throw new RuntimeException("getCoordsOfRoomFacing is null? Wrong direction?");
    }

    public void setupBarriers() {

        BuiltRoom built = BuiltRoom.getBarrier();

        // add barriers to edges
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (i == 0 || j == 0 || i == rooms.length - 1 || j == rooms[i].length - 1) {
                    addBarrier(i, j, built);
                }
            }
        }

    }

    public void fillWithBarriers() {

        BuiltRoom built = BuiltRoom.getBarrier();

        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                if (getRoom(i, j) == null) {
                    addBarrier(i, j, built);
                }
            }
        }

    }

    public boolean checkMissing() {
        boolean did = false;


        for (int x = 0; x < rooms.length; x++) {
            for (int z = 0; z < rooms[x].length; z++) {
                var room = getRoom(x, z);
                if (room != null) {

                    for (Direction dir : room.data.sides.getDoorSides()) {
                        var other = getRoomFacing(dir, x, z);

                        if (other.data.sides.getSideOfDirection(dir.getOpposite()) != RoomSide.DOOR) {
                            System.out.println("Room has no matching door " + x + "_" + z);
                        }
                    }
                }
            }
        }
        return did;
    }



    /*
    public boolean addMissingRooms() {
        this.startFinishing = true;
        boolean did = false;


        List<Direction> dirs = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST);

        for (int x = 0; x < rooms.length; x++) {
            for (int z = 0; z < rooms[x].length; z++) {
                if (getRoom(x, z) != null) {
                    for (Direction dir : dirs) {
                        if (hasDoorButNoRoom(new PointData(x, z), dir)) {
                            this.addRandomRoom(x, z);
                            System.out.println("added missing room at " + x + "_" + z);
                            did = true;
                        }
                    }
                }
            }
        }
        return did;
    }


     */

    /*
    private boolean hasDoorButNoRoom(PointData p, Direction dir) {
        if (getSideOfRoomFacing(dir, p.x, p.y) != RoomSide.DOOR && getRoom(p.x, p.y).data.sides.getSideOfDirection(dir) == RoomSide.DOOR) {
            return true;
        }
        if (getSideOfRoomFacing(dir, p.x, p.y) == RoomSide.DOOR && getRoom(p.x, p.y).data.sides.getSideOfDirection(dir) != RoomSide.DOOR) {
            return true;
        }
        return false;
    }

     */


    public BuiltRoom getRoomFacing(Direction dir, int x, int z) {
        PointData coords = getCoordsOfRoomFacing(dir, x, z);
        if (coords != null) {
            return getRoom(coords.x, coords.y);
        }

        throw new RuntimeException("getRoomFacing is null? Wrong direction?");
    }

    public RoomSide getSideOfRoomFacing(Direction dir, int x, int z) {

        BuiltRoom room = getRoomFacing(dir, x, z);

        if (dir == Direction.NORTH) {
            return room != null ? room.data.sides.SOUTH : RoomSide.UNBUILT;
        } else if (dir == Direction.SOUTH) {
            return room != null ? room.data.sides.NORTH : RoomSide.UNBUILT;
        } else if (dir == Direction.EAST) {
            return room != null ? room.data.sides.WEST : RoomSide.UNBUILT;
        } else if (dir == Direction.WEST) {
            return room != null ? room.data.sides.EAST : RoomSide.UNBUILT;
        }

        throw new RuntimeException("No room found facing in direction of: " + dir.toString() + ": " + x + " , " + z);

    }


    public UnbuiltRoom getUnbuiltFor(int x, int z) {

        RoomSide S = getSideOfRoomFacing(Direction.SOUTH, x, z);
        RoomSide N = getSideOfRoomFacing(Direction.NORTH, x, z);
        RoomSide E = getSideOfRoomFacing(Direction.EAST, x, z);
        RoomSide W = getSideOfRoomFacing(Direction.WEST, x, z);

        RoomSides sides = new RoomSides(S, N, E, W);

        UnbuiltRoom unbuilt = new UnbuiltRoom(sides);

        return unbuilt;
    }


    public void addRandomRoom(int x, int z) {

        UnbuiltRoom unbuilt = getUnbuiltFor(x, z);
        Preconditions.checkNotNull(unbuilt);

        RoomRotation rot = randomDungeonRoom(unbuilt, new PointData(x, z));
        Preconditions.checkNotNull(rot);

        DungeonRoom dRoom = rot.type.getRandomRoom(b.dungeon, b);
        Preconditions.checkNotNull(dRoom);

        BuiltRoom theroom = new BuiltRoom(b.dungeon, rot, dRoom);
        Preconditions.checkNotNull(theroom);

        addRoom(x, z, theroom);

    }

    // todo i think this is the problem
    private void buildConnectedRooms(int x, int z, BuiltRoom room) {

        List<Direction> dirs = room.data.sides.getDoorSides();

        dirs.forEach(dir -> {
            PointData coord = getCoordsOfRoomFacing(dir, x, z);
            if (getRoom(coord.x, coord.y) == null) {
                if (isWithinBounds(coord.x, coord.y)) {
                    if (room.data.sides.getSideOfDirection(dir) == RoomSide.DOOR) {
                        UnbuiltRoom unbuilt = getUnbuiltFor(coord.x, coord.y);

                        Preconditions.checkNotNull(unbuilt);

                        RoomRotation rot = randomDungeonRoom(unbuilt, coord);

                        Preconditions.checkNotNull(rot);

                        DungeonRoom dRoom = rot.type.getRandomRoom(b.dungeon, b);

                        Preconditions.checkNotNull(dRoom);

                        BuiltRoom theroom = new BuiltRoom(b.dungeon, rot, dRoom);

                        Preconditions.checkNotNull(theroom);

                        addRoom(coord.x, coord.y, theroom);
                    }
                }
            }
        });
    }

    public RoomRotation randomDungeonRoom(UnbuiltRoom unbuilt, PointData point) {

        if (shouldStartFinishing(point)) {
            var possible = tryEndRoom(unbuilt);
            if (!possible.isEmpty()) {
                return random(possible);
            } else {
                return randomRoom(unbuilt);
            }
        } else {
            return randomRoom(unbuilt);
        }
    }

    public List<RoomRotation> tryEndRoom(UnbuiltRoom unbuilt) {
        List<RoomType> types = new ArrayList<>();

        types.add(RoomType.END);
        types.add(RoomType.CURVED_HALLWAY);
        types.add(RoomType.STRAIGHT_HALLWAY);
        types.add(RoomType.TRIPLE_HALLWAY);

        for (RoomType type : types) {
            var possible = type.getPossibleFor(unbuilt);

            if (!possible.isEmpty()) {
                return possible;
            }
        }

        return Arrays.asList();

    }


    public RoomRotation randomRoom(UnbuiltRoom unbuilt) {
        List<RoomType> types = new ArrayList<>();
        types.add(RoomType.CURVED_HALLWAY);
        types.add(RoomType.STRAIGHT_HALLWAY);
        types.add(RoomType.FOUR_WAY);
        types.add(RoomType.TRIPLE_HALLWAY);

        List<RoomRotation> possible = new ArrayList<>();

        types.forEach(x -> {
            possible.addAll(x.getPossibleFor(unbuilt));
        });

        if (possible.isEmpty()) {
            // we dont want to end things fast, but if there's nothing else that matches, add an end.
            possible.addAll(RoomType.END.getPossibleFor(unbuilt));

            if (possible.isEmpty()) {
                throw new RuntimeException("No possible rooms at all for unbuilt room, this is horrible.");
            }
        }

        return random(possible);
    }

    public RoomRotation random(List<RoomRotation> list) {
        return RandomUtils.weightedRandom(list, b.rand.nextDouble());
    }

    public void addBarrier(int x, int z, BuiltRoom room) {
        rooms[x][z] = room;
    }

    public void forceSetRoom(int x, int z, BuiltRoom room) {
        rooms[x][z] = room;
    }

    public void addRoom(int x, int z, BuiltRoom room) {

        if (room == null) {
            return;
        }

        if (getRoom(x, z) == null) {
            rooms[x][z] = room;
            amount++;

            if (room.data.type.equals(RoomType.END)) {
                ends++;
            }
            if (room.room.isBoss) {
                bossRooms++;
            }


            this.started = true;

            if (room.data.type != RoomType.END) {
                buildConnectedRooms(x, z, room);
            }


        } else {
            // i think the problem is when adding rooms, its not taking into account unbuilts!
            //System.out.println("Error, setting room that already exists!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuiltDungeon d = (BuiltDungeon) o;

        return d.amount == amount && d.ends == ends;

    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }
}