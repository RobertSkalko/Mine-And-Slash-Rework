package com.robertx22.mine_and_slash.mixins;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

// todo why is this one done??
@Mixin(Player.class)
public class PlayerAnimMixin {
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
