package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.mechanics.base.LeagueMapData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.border.WorldBorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapData {

    public MapItemData map = new MapItemData();

    public List<String> spawnedMechs = new ArrayList<>();

    public String playerUuid = "";

    public int deaths = 0;

    public int chunkX = 0;
    public int chunkZ = 0;

    private HashMap<String, LeagueMapData> leagueDatas = new HashMap<>();

    public LeagueMapData getLeagueData(LeagueMechanic l) {
        if (!leagueDatas.containsKey(l.GUID())) {
            leagueDatas.put(l.GUID(), new LeagueMapData());
        }
        return leagueDatas.get(l.GUID());
    }

    public static MapData newMap(Player p, MapItemData map, MapsData maps) {


        maps.deleteOldMap(p);

        MapData data = new MapData();
        data.playerUuid = p.getStringUUID();
        data.map = map;

        ChunkPos cp = data.randomFree(p.level(), maps);

        data.chunkX = cp.x;
        data.chunkZ = cp.z;


        return data;

    }

    public void trySpawnMechanic(Level level, BlockPos pos) {

        if (LeagueMechanics.NONE.isInsideLeague((ServerLevel) level, pos)) {
            for (LeagueMechanic league : this.map.getLeagueMechanics()) {
                if (!this.spawnedMechs.contains(league.GUID())) {
                    if (RandomUtils.roll(league.chanceToSpawnMechanicAfterKillingMob())) {
                        this.spawnedMechs.add(league.GUID());
                        league.spawnTeleportInMap((ServerLevel) level, pos);
                    }
                }
            }
        }

    }


    public void teleportToMap(Player p) {
        if (p.level().isClientSide) {
            return;
        }
        Load.playerRPGData(p).map.tpbackdim = p.level().dimensionTypeId().location().toString();
        Load.playerRPGData(p).map.tp_back_pos = p.blockPosition().asLong();


        BlockPos pos = getDungeonStartTeleportPos(new ChunkPos(this.chunkX, this.chunkZ));

        Level world = p.level().getServer().getLevel(ResourceKey.create(Registries.DIMENSION, WorldUtils.DUNGEON_DIM_ID));


        world.setBlock(new BlockPos(pos.getX(), 54, pos.getZ()), Blocks.BEDROCK.defaultBlockState(), 2);

        TeleportUtils.teleport((ServerPlayer) p, pos, WorldUtils.DUNGEON_DIM_ID);

    }


    public static BlockPos getDungeonStartTeleportPos(ChunkPos pos) {
        BlockPos p = getStartChunk(pos).getBlockAt(0, 0, 0);
        p = new BlockPos(p.getX() + 8, 57, p.getZ() + 8);
        return p;
    }


    private ChunkPos randomFree(Level level, MapsData maps) {

        ChunkPos pos = null;

        int tries = 0;

        // seems this is how you get the level
        WorldBorder border = level.getServer().getLevel(ResourceKey.create(Registries.DIMENSION, WorldUtils.DUNGEON_DIM_ID)).getWorldBorder();

        int max = (int) (border.getSize() / 16 / 2);

        max = MathHelper.clamp(max, 0, 299999 / 2); // don't be higher than normal mc border

        while (pos == null || maps.getMap(pos).isPresent()) {
            if (tries > 200) {
                System.out.println("Tried too many times to find random dungeon pos and failed, please delete the map dimension folder");
                return null;
            }
            int x = RandomUtils.RandomRange(50, max);
            int z = RandomUtils.RandomRange(50, max);

            pos = new ChunkPos(x, z);
            pos = getStartChunk(pos.getMiddleBlockPosition(50));

        }

        if (tries > 1000) {
            System.out.println("It took more than 1000 tries to find random free dungeon, either you are insanely unlucky, or the world is close to filled! Dungeon worlds are cleared on next server boot if they reach too close to capacity.");
        }

        return pos;

    }

    public static BlockPos getDungeonStartTeleportPos(BlockPos pos) {
        BlockPos p = getStartChunk(pos).getMiddleBlockPosition(55);
        p = new BlockPos(p.getX(), 55, p.getZ());
        return p;
    }


    public boolean isMapHere(BlockPos pos) {
        var cp = getStartChunk(pos);
        return cp.x == chunkX && cp.z == chunkZ;
    }

    public static int DUNGEON_LENGTH = 30;

    public static ChunkPos getStartChunk(BlockPos pos) {
        return getStartChunk(new ChunkPos(pos));
    }

    public static ChunkPos getStartChunk(ChunkPos cp) {
        int chunkX = cp.x;
        int chunkZ = cp.z;
        int distToEntranceX = 11 - (chunkX % DUNGEON_LENGTH);
        int distToEntranceZ = 11 - (chunkZ % DUNGEON_LENGTH);
        chunkX += distToEntranceX;
        chunkZ += distToEntranceZ;
        return new ChunkPos(chunkX, chunkZ);
    }

}
