package com.robertx22.mine_and_slash.vanilla_mc.blocks.bases;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public abstract class OpaqueBlock extends Block {

    public static final DirectionProperty direction = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty light = RedstoneTorchBlock.LIT;

    public OpaqueBlock(Properties properties) {
        super(properties.noOcclusion());

        this.registerDefaultState(
            this.stateDefinition.any()
                .setValue(direction, Direction.NORTH)
                .setValue(light, false));

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
            .setValue(direction, context.getHorizontalDirection()
                .getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(direction, rot.rotate(state.getValue(direction)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(direction)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(direction, light);
    }

}
