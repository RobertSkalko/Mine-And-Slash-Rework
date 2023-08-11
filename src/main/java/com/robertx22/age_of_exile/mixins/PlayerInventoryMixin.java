package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public class PlayerInventoryMixin {


    @Inject(method = "add(Lnet/minecraft/world/item/ItemStack;)Z", at = @At(value = "HEAD"))
    private void hookonStackInserted(ItemStack stack, CallbackInfoReturnable<Boolean> ci) {
        try {
            Inventory inv = (Inventory) (Object) this;

            if (!stack.isEmpty()) {
                if (!inv.player.level().isClientSide) {
                    if (Load.playerRPGData(inv.player).config.salvage.trySalvageOnPickup(inv.player, stack)) {
                        stack.shrink(100);
                    } else {
                        Load.backpacks(inv.player).getBackpacks().tryAutoPickup(stack);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
