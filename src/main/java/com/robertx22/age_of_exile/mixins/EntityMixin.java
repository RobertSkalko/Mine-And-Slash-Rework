package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Spider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getLightLevelDependentMagicValue", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(CallbackInfoReturnable<Float> cir) {

        try {
            Entity en = (Entity) (Object) this;
            if (en instanceof Spider) {
                if (WorldUtils.isMapWorldClass(en.level())) {
                    cir.setReturnValue(0F);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
