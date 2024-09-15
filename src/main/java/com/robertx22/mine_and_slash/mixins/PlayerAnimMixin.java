package com.robertx22.mine_and_slash.mixins;

import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import org.spongepowered.asm.mixin.Mixin;

// todo why is this one done??
@Mixin(ItemInHandLayer.class)
public class PlayerAnimMixin {

    /*
    @Inject(method = "renderArmWithItem", cancellable = true, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void changeItemLocation(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm arm, PoseStack matrices, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (livingEntity instanceof IAnimatedPlayer player) {

            // todo
            var animation = (ModifierLayer<IAnimation>) PlayerAnimationAccess.getPlayerAssociatedData((AbstractClientPlayer) player).get(SpellAnimations.ANIMATION_RESOURCE);
            if (animation != null) {
                matrices.popPose();
                ci.cancel();
            }


        }
    }

     */

    /*
    //Unique params might be renamed
    @Unique
    private final Map<ResourceLocation, IAnimation> modAnimationData = new HashMap<>();
    @Unique
    private final AnimationStack animationStack = createAnimationStack();
    @Unique
    private final AnimationApplier animationApplier = new AnimationApplier(animationStack);


    @SuppressWarnings("ConstantConditions")
    @Unique
    private AnimationStack createAnimationStack() {
        AnimationStack stack = new AnimationStack();
        if (AbstractClientPlayer.class.isInstance(this)) {
            PlayerAnimationFactory.ANIMATION_DATA_FACTORY.prepareAnimations((AbstractClientPlayer) (Object) this, stack, modAnimationData);
            PlayerAnimationAccess.REGISTER_ANIMATION_EVENT.invoker().registerAnimation((AbstractClientPlayer) (Object) this, stack);
        }
        return stack;
    }

    @Override
    public AnimationStack getAnimationStack() {
        return animationStack;
    }

    @Override
    public AnimationApplier playerAnimator_getAnimation() {
        return animationApplier;
    }

    @Override
    public @Nullable IAnimation playerAnimator_getAnimation(@NotNull ResourceLocation id) {
        return modAnimationData.get(id);
    }

    @Override
    public @Nullable IAnimation playerAnimator_setAnimation(@NotNull ResourceLocation id, @Nullable IAnimation animation) {
        if (animation == null) {
            return modAnimationData.remove(id);
        } else {
            return modAnimationData.put(id, animation);
        }
    }

    @SuppressWarnings("ConstantConditions")
    // When injected into PlayerEntity, instance check can tell if a ClientPlayer or ServerPlayer
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (AbstractClientPlayer.class.isInstance(this)) {
            animationStack.tick();
        }
    }

     */
}
