package com.robertx22.mine_and_slash.database.data.league;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.maps.LeagueData;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueBlockData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class MapBossLeague extends LeagueMechanic {

    @Override
    public LeagueStructure getStructure(MapItemData map) {
        return new LeagueStructure(this) {
            @Override
            public BlockPos getTeleportPos(BlockPos pos) {
                BlockPos p = MapData.getStartChunk(pos).getBlockAt(0, 0, 0);
                p = new BlockPos(p.getX() + 10, startY() + 5 + 3, p.getZ() + 22);
                return p;
            }


            @Override
            public LeaguePiecesList getPieces(MapItemData map) {
                var arena = ExileDB.BossArena().get(map.arena);
                return new LeaguePiecesList(Arrays.asList(new LeagueStructurePieces(arena.size, "map_boss/" + map.arena)));
            }

            @Override
            public int startY() {
                return -60;
            }


            @Override
            public boolean isInsideLeague(ServerLevel level, BlockPos pos) {
                return pos.getY() >= startY() && pos.getY() <= (startY() + 30);
            }
        };
    }

    @Override
    public int getDefaultSpawns() {
        return 0;
    }

    @Override
    public void onMapStartSetup(LeagueData data) {

    }

    @Override
    public void onKillMob(MapData map, LootInfo info) {

    }

    @Override
    public Block getTeleportBlock() {
        return Blocks.AIR;
    }

    @Override
    public void spawnMechanicInMap(ServerLevel level, BlockPos pos) {
        level.setBlock(pos, SlashBlocks.PROPHECY_ALTAR.get().defaultBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data) {

    }

    @Override
    public ChatFormatting getTextColor() {
        return ChatFormatting.RED;
    }


    @Override
    public String GUID() {
        return LeagueMechanics.MAP_BOSS_ID;
    }

    @Override
    public int Weight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Map Boss Mechanic";
    }
}
