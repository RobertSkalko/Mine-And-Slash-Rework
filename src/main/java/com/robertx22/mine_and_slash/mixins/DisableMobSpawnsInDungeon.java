package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnPlacements.class)
public class DisableMobSpawnsInDungeon {

    @Inject(method = "checkSpawnRules", at = @At(value = "HEAD"), cancellable = true)
    private static <T extends Entity> void disableCanSpawn(EntityType<T> pEntityType, ServerLevelAccessor pServerLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom, CallbackInfoReturnable<Boolean> cir) {
        if (WorldUtils.isMapWorldClass(pServerLevel.getLevel())) {
            if (pSpawnType == MobSpawnType.NATURAL) {
                cir.setReturnValue(false);
            }
        }
    }

}
