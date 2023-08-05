package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnItemInteract;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this exists in 1.17 ItemStack and Item code. Remove when porting
@Mixin(AbstractContainerMenu.class)
public abstract class ItemInteractionHook {

    @Inject(method = "doClick(IILnet/minecraft/inventory/container/ClickType;Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/item/ItemStack;", cancellable = true, at = @At(value = "HEAD"))
    public void method_30010(int i, int j, ClickType slotActionType, Player player, CallbackInfoReturnable<ItemStack> ci) {
        AbstractContainerMenu screen = (AbstractContainerMenu) (Object) this;
        try {
            OnItemInteract.on(screen, i, j, slotActionType, player, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
