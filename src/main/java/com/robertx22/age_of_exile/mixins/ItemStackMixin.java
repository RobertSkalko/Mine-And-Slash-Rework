package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.mixin_methods.TooltipMethod;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ItemStack.class, priority = Integer.MAX_VALUE)
public abstract class ItemStackMixin {
    public ItemStackMixin() {
    }

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(Level world, Player p, InteractionHand pUsedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {

        try {
            ItemStack stack = (ItemStack) (Object) this;

            if (WorldUtils.isDungeonWorld(world)) {
                if (ServerContainer.get().isItemBanned(stack.getItem())) {
                    cir.setReturnValue(InteractionResultHolder.fail(stack));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // copied from TooltipCallback fabric event
    @Inject(method = {"getTooltipLines"}, at = {@At("RETURN")})
    private void getTooltip(Player entity, TooltipFlag tooltipContext, CallbackInfoReturnable<List<Component>> list) {
        ItemStack stack = (ItemStack) (Object) this;
        TooltipMethod.getTooltip(stack, entity, tooltipContext, list);
    }


}
