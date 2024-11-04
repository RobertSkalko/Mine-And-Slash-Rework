package com.robertx22.mine_and_slash.database.data.league;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.maps.LeagueData;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.maps.MapItemData;
import com.robertx22.mine_and_slash.maps.ProcessChunkBlocks;
import com.robertx22.mine_and_slash.mechanics.base.LeagueBlockData;
import com.robertx22.mine_and_slash.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;

public class MapRewardLeague extends LeagueMechanic {

    @Override
    public LeagueStructure getStructure(MapItemData map) {
        return new LeagueStructure(this) {

            @Override
            public LeaguePiecesList getPieces(MapItemData map) {
                // todo hardcoded to this for now
                // var arena = ExileDB.BossArena().get(map.arena);
                return new LeaguePiecesList(Arrays.asList(new LeagueStructurePieces(1, "map_reward/sandstone")));
            }

            @Override
            public int startY() {
                return -60 + 35;
            }
        };
    }

    public void generateManually(MapData map, ServerLevel world, BlockPos pos) {
        world.setBlock(pos, SlashBlocks.REWARD_TELEPORT.get().defaultBlockState(), Block.UPDATE_ALL);

        ChunkPos cpos = MapData.getStartChunk(pos);

        var chunk = world.getChunk(cpos.x, cpos.z);

        getStructure(map.map).tryGenerate(world, cpos);
        ProcessChunkBlocks.leagueSpawn(world, chunk);
        ProcessChunkBlocks.generateData(world, chunk);
    }

    @Override
    public boolean gensRightAway(MapData map) {
        return false;
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
        // level.setBlock(pos, SlashBlocks.PROPHECY_ALTAR.get().defaultBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data) {

    }

    @Override
    public ChatFormatting getTextColor() {
        return ChatFormatting.GOLD;
    }


    @Override
    public String GUID() {
        return LeagueMechanics.MAP_REWARD_ID;
    }

    @Override
    public int Weight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Map Reward Mechanic";
    }
}
