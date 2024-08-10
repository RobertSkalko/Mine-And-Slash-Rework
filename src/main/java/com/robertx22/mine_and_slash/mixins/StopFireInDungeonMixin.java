package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireBlock.class)
public class StopFireInDungeonMixin {


    @Inject(method = "canSurvive", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        try {
            if (world instanceof Level l) {// todo will this work
                if (WorldUtils.isDungeonWorld(l)) {
                    if (world.getBlockState(pos.below())
                            .getBlock() != Blocks.NETHERRACK) {
                        ci.setReturnValue(false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
