package com.robertx22.age_of_exile.mixin_ducks;


import net.minecraft.world.level.block.state.BlockState;

public interface FallingBlockAccessor {

    void setDestroyedOnLanding(boolean bool);

    void setBlockState(BlockState bool);

}