package com.robertx22.mine_and_slash.mixins;

import net.minecraft.server.ServerFunctionManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerFunctionManager.class)
public class TestMixin {
    /*
    @Inject(method = "postReload", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(ServerFunctionLibrary pReloader, CallbackInfo ci) {
        try {
            ResourceLocation TICK_FUNCTION_TAG = new ResourceLocation("tick");

            var ticking = ImmutableList.copyOf(pReloader.getTag(TICK_FUNCTION_TAG));

            ExileLog.get().log("FUNCTIONS USED:");

            for (CommandFunction fu : ticking) {
                ExileLog.get().log(fu.getId().toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}
