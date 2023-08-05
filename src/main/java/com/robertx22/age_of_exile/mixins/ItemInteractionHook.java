package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// this exists in 1.17 ItemStack and Item code. Remove when porting
@Mixin(AbstractContainerMenu.class)
public abstract class ItemInteractionHook {

    @Inject(method = "doClick", cancellable = true, at = @At(value = "HEAD"))
    public void method_30010(int i, int j, ClickType pClickType, Player player, CallbackInfo ci) {
        AbstractContainerMenu screen = (AbstractContainerMenu) (Object) this;
        try {
            OnItemInteract.on(screen, i, j, pClickType, player, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
