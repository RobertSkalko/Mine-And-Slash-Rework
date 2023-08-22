package com.robertx22.age_of_exile.mixins;

import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public class BowMixin {
    /*
    @Inject(method = "getUseDuration", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(CallbackInfoReturnable<Integer> cir) {
        try {
            ItemStack stack = (ItemStack) (Object) this;
            if (stack.is(Items.BOW)) {
                if (stack.getOrCreateTag().getBoolean("instant")) {
                    cir.setReturnValue(0);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}
