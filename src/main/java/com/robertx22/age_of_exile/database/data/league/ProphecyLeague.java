package com.robertx22.age_of_exile.database.data.league;

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
import net.minecraft.world.level.block.Blocks;

public class ProphecyLeague extends LeagueMechanic {
    @Override
    public LeagueStructure getStructure(MapItemData map) {
        return LeagueStructure.EMPTY;
    }

    @Override
    public int getDefaultSpawns() {
        return 5;
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
        return ChatFormatting.LIGHT_PURPLE;
    }


    @Override
    public String GUID() {
        return "prophecy";
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public String locNameForLangFile() {
        return "Prophecy League";
    }
}
