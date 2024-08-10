package com.robertx22.mine_and_slash.mixin_ducks;


import net.minecraft.world.level.block.state.BlockState;

public interface FallingBlockAccessor {

    void setDestroyedOnLanding(boolean bool);

    void setBlockState(BlockState bool);

}