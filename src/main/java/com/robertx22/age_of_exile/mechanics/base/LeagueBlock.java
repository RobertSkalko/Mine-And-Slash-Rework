package com.robertx22.age_of_exile.mechanics.base;

import com.robertx22.age_of_exile.maps.MapBlockEntity;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
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

public class LeagueBlock extends BaseEntityBlock {

    public LeagueBlock() {
        super(BlockBehaviour.Properties.of().strength(2).noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MapBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    /*
        @Override
        public InteractionResult use(BlockState pState, Level level, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {


            if (!level.isClientSide) {

                MapItemData data = StackSaving.MAP.loadFrom(p.getItemInHand(pHand));


                if (WorldUtils.isDungeonWorld(level)) {
                    Load.playerRPGData(p).map.teleportBack(p);

                } else {


                }

            }

            return InteractionResult.SUCCESS;
        }

     */
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, SlashBlockEntities.LEAGUE.get(), LeagueBlock::serverTick);
    }

    public static void serverTick(Level pLevel, BlockPos pPos, BlockState pState, LeagueBlockEntity be) {
        try {
            if (be.data.finished) {
                pLevel.removeBlock(pPos, false); // todo will this be ok?
                return;
            }

            be.data.getMechanic((ServerLevel) pLevel, pPos).onTick((ServerLevel) pLevel, pPos, be, be.data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
