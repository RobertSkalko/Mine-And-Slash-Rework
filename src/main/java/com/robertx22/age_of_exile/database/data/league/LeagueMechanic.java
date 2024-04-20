package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.maps.LeagueData;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mmorpg.ModErrors;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public abstract class LeagueMechanic implements ExileRegistry<LeagueMechanic> {
    public static MapField<String> STRUCTURE = new MapField<>("structure");

    public static LeagueMechanic getMechanicFromMob(LivingEntity en) {

        return getMechanicFromPosition((ServerLevel) en.level(), en.blockPosition());
    }

    public static LeagueMechanic getMechanicFromPosition(ServerLevel sw, BlockPos pos) {

        var list = ExileDB.LeagueMechanics().getFilterWrapped(x -> x.isInsideLeague(sw, pos)).list;

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return LeagueMechanics.NONE;
    }

    public float getBaseSpawnChance() {
        return 100;
    }

    public abstract int getDefaultSpawns();

    public final void onMapStartSetupBase(LeagueData data) {

        data.remainingSpawns = getDefaultSpawns();

        if (true) { // todo maybe separate mechanics that spawn structures and ones that dont
            data.map.put(STRUCTURE, RandomUtils.weightedRandom(getPieces().list).folder);
        }
    }

    public abstract void onMapStartSetup(LeagueData data);

    public abstract void onKillMob(MapData map, LootInfo info);

    public final void teleportToStartOfLeague(Player p) {
        var map = Load.mapAt(p.level(), p.blockPosition());

        if (map != null) {
            var lo = map.leagues.get(this).spawn_pos;
            var tp = BlockPos.of(lo);

            if (lo != 0L) {
                Load.player(p).map.tp_back_from_league_pos = p.blockPosition().asLong();
                TeleportUtils.teleport((ServerPlayer) p, tp);
            }
        }

        // BlockPos tp = getTeleportPos(p.blockPosition());
        //Load.playerRPGData(p).map.tp_back_from_league_pos = p.blockPosition().asLong();
        //TeleportUtils.teleport((ServerPlayer) p, tp);
    }

    public final void teleportBackToDungeon(Player p) {
        Load.player(p).map.teleportBackFromLeagueToDungeon(p);
    }

    public abstract void spawnMechanicInMap(ServerLevel level, BlockPos pos);


    public boolean isEmpty() {
        return false;
    }

    public abstract void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data);

    public abstract BlockPos getTeleportPos(BlockPos pos);


    public abstract LeaguePiecesList getPieces();


    public abstract int startY();


    public abstract boolean isInsideLeague(ServerLevel level, BlockPos pos);


    public final void tryGenerate(ServerLevel level, ChunkPos pos, Random ran) {
        try {

            var list = getPieces();

            if (!getPieces().list.isEmpty()) {
                var map = Load.mapAt(level, pos.getBlockAt(0, 0, 0));

                var s = map.leagues.get(this).map.get(STRUCTURE);

                LeagueStructurePieces pieces = list.get(s);

                var room = pieces.getRoomForChunk(pos);
                if (room != null) {
                    generateStructure(level, room, pos);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected boolean generateStructure(LevelAccessor world, ResourceLocation room, ChunkPos cpos) {


        try {
            var opt = world.getServer().getStructureManager().get(room);
            if (opt.isPresent()) {
                var template = opt.get();
                StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE)
                        .setIgnoreEntities(false);

                settings.setBoundingBox(settings.getBoundingBox());

                BlockPos position = cpos.getBlockAt(0, startY(), 0);

                if (template == null) {
                    System.out.println("FATAL ERROR: Structure does not exist (" + room.toString() + ")");
                    return false;
                }
                settings.setRotation(Rotation.NONE);

                template.placeInWorld((ServerLevelAccessor) world, position, position, settings, world.getRandom(), Block.UPDATE_CLIENTS);
            }
        } catch (Exception e) {
            ModErrors.print(e);
            return false;
        }

        return true;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LEAGUE_MECHANIC;
    }
}
