package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.overlays.GuiPosition;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.item.ItemStack;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class StopOffhandRenderMixin {

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", opcode = Opcodes.GETFIELD))
    private boolean injected(ItemStack instance) {
        if (ClientConfigs.getConfig().GUI_POSITION.get() == GuiPosition.BOTTOM_CENTER) {
            return true;
        }
        return instance.isEmpty();
    }
}
