package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class StopMapMobDropsMixin {
    // all the vanilla loot will  come from chests, that you can  take or not
    // we dont want map mobs to drop vanilla loot, too much clutter
    @Inject(method = "getLootTable", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(CallbackInfoReturnable<ResourceLocation> cir) {

        try {
            Mob mob = (Mob) (Object) this;
            if (WorldUtils.isMapWorldClass(mob.level())) {
                cir.setReturnValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
