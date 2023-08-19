package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LeagueMechanics {

    public static LeagueMechanic NONE = new LeagueMechanic() {
        @Override
        public void onTick(ServerLevel level, BlockPos pos, LeagueBlockEntity be, LeagueBlockData data) {

        }

        @Override
        public BlockPos getTeleportPos(BlockPos pos) {
            return pos;
        }

        @Override
        public LeagueStructurePieces getPieces() {
            return null;
        }

        @Override
        public int startY() {
            return 0;
        }

        @Override
        public boolean isInsideLeague(ServerLevel level, BlockPos pos) {
            return false;
        }

        @Override
        public String GUID() {
            return "none";
        }

        @Override
        public int Weight() {
            return 0;
        }
    };

    public static LeagueMechanic HARVEST = new HarvestLeague();


    public static void init() {
        HARVEST.registerToExileRegistry();
    }
}
