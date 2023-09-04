package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Screen.class)
public class JeiMixin {
    /*
    @Inject(method = "hasShiftDown", at = @At(value = "HEAD"), cancellable = true)
    private static void hookLoot(CallbackInfoReturnable<Boolean> cir) {
        try {
            if (Minecraft.getInstance().screen instanceof CraftingStationScreen) {
                cir.setReturnValue(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}


