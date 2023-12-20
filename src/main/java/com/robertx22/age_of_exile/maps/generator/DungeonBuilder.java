package com.robertx22.age_of_exile.maps.generator;


import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.DungeonRoom;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.dungeon_reg.Dungeon;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.world.level.ChunkPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonBuilder {


    public static Random createRandom(long worldSeed, ChunkPos cpos) {
        int chunkX = MapData.getStartChunk(cpos.getMiddleBlockPosition(55)).x;
        int chunkZ = MapData.getStartChunk(cpos.getMiddleBlockPosition(55)).z;
        long newSeed = (worldSeed + (long) (chunkX * chunkX * 4987142) + (long) (chunkX * 5947611) + (long) (chunkZ * chunkZ) * 4392871L + (long) (chunkZ * 389711) ^ worldSeed);
        return new Random(newSeed);
    }

    public DungeonBuilder(long worldSeed, ChunkPos cpos) {

        rand = createRandom(worldSeed, cpos);

        this.dungeon = RandomUtils.weightedRandom(ExileDB.Dungeons().getFilterWrapped(x -> x.can_be_main).list, rand.nextDouble());

        //       this.dungeon = ExileDB.Dungeons().get("pyramid"); // todo

        this.size = RandomUtils.RandomRange(ServerContainer.get().MIN_MAP_ROOMS.get(), ServerContainer.get().MAX_MAP_ROOMS.get(), rand);

        // todo this needs the same random if i'll use at world gen async, if i do it myself, it doesnt

        if (RandomUtils.roll(5, rand)) {
            this.maxBossRooms++;
        }
    }


    public Dungeon dungeon;
    public BuiltDungeon builtDungeon;
    public final Random rand;
    public int size;
    public boolean isTesting = false;
    public int maxBossRooms = 1;


    public void build() {
        builtDungeon = new BuiltDungeon(size, this);

        builtDungeon.setupBarriers();

        setupEntrance();

        builtDungeon.fillWithBarriers();

        // builtDungeon.checkMissing();

    }


    public RoomRotation random(List<RoomRotation> list) {
        return RandomUtils.weightedRandom(list, rand.nextDouble());
    }

    private void setupEntrance() {
        DungeonRoom entranceRoom = RoomType.ENTRANCE.getRandomRoom(dungeon, this);

        List<RoomRotation> possible = new ArrayList<>();
        possible.addAll(RoomType.ENTRANCE.getRotations());
        RoomRotation rotation = random(possible);

        BuiltRoom entrance = new BuiltRoom(this.dungeon, rotation, entranceRoom);

        int mid = builtDungeon.getMiddle();
        builtDungeon.addRoom(mid, mid, entrance);
    }

}