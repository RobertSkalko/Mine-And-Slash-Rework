package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.mmorpg.ModErrors;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Arrays;
import java.util.Random;

public abstract class LeagueStructure {

    public static LeagueStructure EMPTY = new LeagueStructure(LeagueMechanics.NONE) {

        @Override
        public BlockPos getTeleportPos(BlockPos pos) {
            return pos;
        }

        @Override
        public LeaguePiecesList getPieces(MapItemData map) {
            return new LeaguePiecesList(Arrays.asList());
        }

        @Override
        public int startY() {
            return 0;
        }

        @Override
        public boolean isInsideLeague(ServerLevel level, BlockPos pos) {
            var md = Load.mapAt(level, pos);
            var map = md.map;

            // if it's not inside any other league mechanic, its in the normal map
            return ExileDB.LeagueMechanics().getList().stream().filter(x -> !x.getStructure(map).getPieces(map).list.isEmpty()).allMatch(x -> !x.getStructure(map).isInsideLeague(level, pos));
        }
    };

    public LeagueMechanic league;

    public LeagueStructure(LeagueMechanic league) {
        this.league = league;
    }

    public static LeagueMechanic getMechanicFromPosition(ServerLevel sw, BlockPos pos) {

        var md = Load.mapAt(sw, pos);
        var map = md.map;

        var list = ExileDB.LeagueMechanics().getFilterWrapped(x -> x.getStructure(map) != null && x.getStructure(map).isInsideLeague(sw, pos)).list;

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return LeagueMechanics.NONE;
    }

    public abstract BlockPos getTeleportPos(BlockPos pos);


    public abstract LeaguePiecesList getPieces(MapItemData map);


    public abstract int startY();


    public abstract boolean isInsideLeague(ServerLevel level, BlockPos pos);


    public final void tryGenerate(ServerLevel level, ChunkPos pos, Random ran) {
        try {
            var md = Load.mapAt(level, pos.getBlockAt(0, 0, 0));

            if (md == null || md.map == null) {
                return;
            }

            var map = md.map;

            var list = getPieces(map);

            if (!getPieces(map).list.isEmpty()) {

                var s = md.leagues.get(league).map.get(this.league.getStructureId());

                LeagueStructurePieces pieces = list.get(s);

                var room = pieces.getRoomForChunk(pos);
                if (room != null) {
                    generateStructure(map, level, room, pos);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected boolean generateStructure(MapItemData map, LevelAccessor world, ResourceLocation room, ChunkPos cpos) {


        try {
            if (!getPieces(map).list.isEmpty()) {


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
            }
        } catch (Exception e) {
            ModErrors.print(e);
            return false;
        }

        return true;
    }


    public final void teleportToStartOfLeague(Player p) {
        var map = Load.mapAt(p.level(), p.blockPosition());

        if (map != null) {
            var lo = map.leagues.get(league).spawn_pos;
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
}
