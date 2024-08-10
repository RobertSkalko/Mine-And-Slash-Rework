package com.robertx22.mine_and_slash.mechanics.base;

import com.robertx22.mine_and_slash.database.data.league.LeagueStructure;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class LeagueTeleportBlock extends Block {
    String league;

    public LeagueTeleportBlock(String league) {
        super(BlockBehaviour.Properties.of().strength(2).noOcclusion());
        this.league = league;
    }

    @Override
    public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {


        if (!level.isClientSide) {

            if (WorldUtils.isDungeonWorld(level)) {

                var mech = LeagueStructure.getMechanicFromPosition((ServerLevel) level, pPos);

                var map = Load.mapAt(level, pPos).map;

                if (mech.GUID().equals(league)) {
                    mech.getStructure(map).teleportBackToDungeon(p);
                } else {
                    ExileDB.LeagueMechanics().get(league).getStructure(map).teleportToStartOfLeague(p);
                }

            }
        }

        return InteractionResult.SUCCESS;
    }
}
