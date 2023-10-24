package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.age_of_exile.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ProfessionBlock extends BaseEntityBlock implements WorldlyContainerHolder {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public String profession;

    public ProfessionBlock(String profession) {
        super(Properties.copy(Blocks.CRAFTING_TABLE).noOcclusion());
        this.profession = profession;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));


    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void onRemove(BlockState pState, Level level, BlockPos p, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(p);
            if (blockentity instanceof ProfessionBlockEntity be) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, p, be.inventory);
                    ItemEntity en = new ItemEntity(level, p.getX(), p.getY(), p.getZ(), asItem().getDefaultInstance());
                    level.addFreshEntity(en);
                }
                level.updateNeighbourForOutputSignal(p, this);
            }
        }
        super.onRemove(pState, level, p, pNewState, pIsMoving);
    }

    // add rotation
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player p, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide) {

            ProfessionBlockEntity be = (ProfessionBlockEntity) pLevel.getBlockEntity(pPos);

            if (be.owner.isEmpty()) {
                be.owner = p.getStringUUID();
            } else {
                if (!be.owner.equals(p.getStringUUID())) {
                    p.sendSystemMessage(Chats.NOT_OWNER.locName());
                    return InteractionResult.FAIL;
                }
            }

           
            p.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                    return new CraftingStationMenu(pContainerId, pPlayerInventory, be.inventory, be.show);
                }
            });

        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ProfessionBlockEntity(pPos, pState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return createTickerHelper(type, SlashBlockEntities.PROFESSION.get(), pLevel.isClientSide ? null : ProfessionBlock::serverTick);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ProfessionBlockEntity be) {
        be.tick(level);
    }

    @Override
    public WorldlyContainer getContainer(BlockState pState, LevelAccessor pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof ProfessionBlockEntity be) {
            return be.inventory;
        }
        return null;
    }
}
