package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mixin_methods.TooltipWidth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Tooltip.class)
public class TooltipMixin {
    @Inject(method = "splitTooltip", at = @At(value = "HEAD"), cancellable = true)
    private static void sdsdsdsdsd(Minecraft mc, Component msg, CallbackInfoReturnable<List<FormattedCharSequence>> cir) {
        try {
            if (ClientConfigs.getConfig().MODIFY_TOOLTIP_LENGTH.get()) {
                cir.setReturnValue(mc.font.split(msg, TooltipWidth.getMax(msg)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
