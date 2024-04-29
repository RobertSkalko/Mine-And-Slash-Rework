package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.league.LeaguePiecesList;
import com.robertx22.age_of_exile.database.data.league.LeagueStructure;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.maps.LeagueData;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class UberMechanic extends LeagueMechanic {

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
                return map.getUber().structure;
               /*
                return new LeaguePiecesList(Arrays.asList(
                        new LeagueStructurePieces(2, "harvest/circle") // todo need custom
                ));

                */
            }

            @Override
            public int startY() {
                return 85 + 50;
            }


            @Override
            public boolean isInsideLeague(ServerLevel level, BlockPos pos) {
                return pos.getY() >= startY() && pos.getY() <= (startY() + 30);
            }
        };
    }

    @Override
    public int getDefaultSpawns() {
        return 1;
    }

    @Override
    public void onMapStartSetup(LeagueData data) {

    }

    @Override
    public Block getTeleportBlock() {
        return SlashBlocks.UBER_TELEPORT.get();
    }

    @Override
    public float getBaseSpawnChance() {
        return 0;
    }


    @Override
    public void onKillMob(MapData map, LootInfo info) {

    }

    @Override
    public void spawnMechanicInMap(ServerLevel level, BlockPos pos) {

        level.setBlock(pos, SlashBlocks.UBER_TELEPORT.get().defaultBlockState(), 2);
    }


    @Override
    public void onTick(MapData map, ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data) {

    }

    @Override
    public ChatFormatting getTextColor() {
        return ChatFormatting.LIGHT_PURPLE;
    }

    @Override
    public String locNameForLangFile() {
        return "Uber Boss Mechanic";
    }

    @Override
    public String GUID() {
        return LeagueMechanics.UBER_ID;
    }

    @Override
    public int Weight() {
        return 0;
    }
}
