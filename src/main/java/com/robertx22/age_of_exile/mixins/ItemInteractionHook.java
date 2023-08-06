package com.robertx22.age_of_exile.mixins;

import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;

// this exists in 1.17 ItemStack and Item code. Remove when porting
@Mixin(AbstractContainerMenu.class)
public abstract class ItemInteractionHook {

    /*
    @Inject(method = "doClick", cancellable = true, at = @At(value = "RETURN"))
    public void method_30010(int i, int j, ClickType pClickType, Player player, CallbackInfo ci) {
        AbstractContainerMenu screen = (AbstractContainerMenu) (Object) this;
        try {
            OnItemInteract.on(screen, i, j, pClickType, player, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}
