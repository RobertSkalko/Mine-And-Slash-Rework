package com.robertx22.mine_and_slash.content.ubers;

import com.robertx22.mine_and_slash.database.data.league.LeaguePiecesList;
import com.robertx22.mine_and_slash.database.data.league.LeagueStructurePieces;
import net.minecraft.world.entity.EntityType;

import java.util.Arrays;

public class UberBosses {

    public static void init() {


        UberEnum.WITHER.createBoss(EntityType.WITHER, new LeaguePiecesList(Arrays.asList(
                new LeagueStructurePieces(2, "uber/river")
        )), x -> {

        });
    }
}
