package com.robertx22.age_of_exile.maps.processors.league;

import com.robertx22.age_of_exile.database.data.league.LeagueStructure;
import com.robertx22.age_of_exile.maps.generator.ChunkProcessData;
import com.robertx22.age_of_exile.maps.processors.DataProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class LeagueTpBackProcessor extends DataProcessor {

    public LeagueTpBackProcessor() {
        super("league_back", Type.EQUALS);
    }

    @Override
    public boolean canSpawnLeagueMechanic() {
        return false;
    }

    @Override
    public void processImplementation(String key, BlockPos pos, Level world, ChunkProcessData data) {

        var league = LeagueStructure.getMechanicFromPosition((ServerLevel) world, pos);

        world.setBlock(pos, league.getTeleportBlock().defaultBlockState(), 2);

    }
}
