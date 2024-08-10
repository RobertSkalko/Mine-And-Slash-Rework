package com.robertx22.mine_and_slash.mechanics.base;

import com.robertx22.mine_and_slash.database.data.league.LeagueMechanic;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public class LeagueBlockData {

    public StructureRadius size = new StructureRadius(0, 0, 0);

    public int ticks = 0;
    public int kills = 0;

    public boolean finished = false;

    public LeagueMechanic getMechanic(ServerLevel sw, BlockPos pos) {

        var map = Load.mapAt(sw, pos).map;

        return ExileDB.LeagueMechanics().getFilterWrapped(x -> x.getStructure(map).isInsideLeague(sw, pos)).list.get(0);
    }

    public static class StructureRadius {
        public int xRadius;
        public int zRadius;
        public int yHeight;

        public StructureRadius(int xRadius, int zRadius, int yHeight) {
            this.xRadius = xRadius;
            this.zRadius = zRadius;
            this.yHeight = yHeight;
        }
    }
}
