package com.robertx22.mine_and_slash.mechanics.base;

import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LeagueControlBlock extends BaseEntityBlock {

    public LeagueControlBlock() {
        super(BlockBehaviour.Properties.of().strength(2).noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LeagueControlBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, SlashBlockEntities.LEAGUE.get(), LeagueControlBlock::serverTick);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, LeagueControlBlockEntity be) {
        try {
            if (be.data.finished) {
                pLevel.removeBlock(pPos, false); // todo will this be ok?
                return;
            }
            if (!be.getPlayers().isEmpty()) {
                var map = Load.mapAt(pLevel, pPos);

                if (map != null) {
                    be.data.getMechanic((ServerLevel) pLevel, pPos).onTick(map, (ServerLevel) pLevel, pPos, be, be.data);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
