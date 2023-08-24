package com.robertx22.age_of_exile.mechanics.base;

import com.robertx22.age_of_exile.database.data.league.LeagueMechanic;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
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

                var mech = LeagueMechanic.getMechanicFromPosition((ServerLevel) level, pPos);

                if (mech.GUID().equals(league)) {
                    mech.teleportBackToDungeon(p);
                } else {
                    ExileDB.LeagueMechanics().get(league).teleportToStartOfLeague(p);
                }

            }
        }

        return InteractionResult.SUCCESS;
    }
}