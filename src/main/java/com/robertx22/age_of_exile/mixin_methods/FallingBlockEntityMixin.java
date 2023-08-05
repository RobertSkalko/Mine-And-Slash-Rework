package com.robertx22.age_of_exile.mixin_methods;


import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin implements FallingBlockAccessor {

    @Accessor(value = "cancelDrop")
    @Override
    public abstract void setDestroyedOnLanding(boolean bool);

    @Accessor(value = "blockState")
    @Override
    public abstract void setBlockState(BlockState state);

}