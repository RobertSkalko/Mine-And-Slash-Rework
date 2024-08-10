package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.IOneOfATypePotion;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CanEntityHavePotionMixin {

    public static void hook(LivingEntity en, MobEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        if (!canAddPotion(en, effect.getEffect())) {
            ci.setReturnValue(false);
        }
    }

    public static boolean canAddPotion(LivingEntity en, MobEffect effect) {

        try {

            if (effect instanceof IOneOfATypePotion) {
                IOneOfATypePotion one = (IOneOfATypePotion) effect;

                if (one.isOneOfAKind()) {
                    if (en.getActiveEffects()
                        .stream()
                        .anyMatch(x -> {
                            if (x.getEffect() instanceof IOneOfATypePotion) {
                                IOneOfATypePotion ot = (IOneOfATypePotion) x.getEffect();
                                if (ot.getOneOfATypeType()
                                    .equals(one.getOneOfATypeType())) {
                                    return true;
                                }
                                if (x.getEffect()
                                    .equals(effect)) {
                                    return true;
                                }
                            }
                            return false;
                        })) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }
}
