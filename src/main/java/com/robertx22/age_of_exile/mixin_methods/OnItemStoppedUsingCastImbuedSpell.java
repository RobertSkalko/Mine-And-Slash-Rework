package com.robertx22.age_of_exile.mixin_methods;

public class OnItemStoppedUsingCastImbuedSpell {

    public static void register() {

        /*
        ForgeEvents.registerForgeEvent(LivingEntityUseItemEvent.Stop.class, event -> {
            if (!event.getEntity().level().isClientSide) {
                //onStoppedUsing(event.getItem(), event.getEntity(), event.getDuration());
            }
        });
         */

    }

    /*
    public static boolean canCastImbuedSpell(LivingEntity user) {

        ItemStack stack = user.getUseItem();

        int remainingUseTicks = user.getUseItemRemainingTicks();

        boolean shoot = false;
        int usedur = stack.getItem()
                .getUseDuration(stack) - remainingUseTicks;

        if (stack.getItem() instanceof BowItem && usedur > 20 * 1) {
            shoot = true;
        }
        if (stack.getItem() instanceof BowItem) {
            int i = stack.getItem()
                    .getUseDuration(stack) - remainingUseTicks;
            float f = BowItem.getPowerForTime(i);
            if (f == 1F) {
                shoot = true;

            }
        }

        return shoot;

    }


     */
    /*
    public static void onStoppedUsing(ItemStack stack, LivingEntity user, int remainingUseTicks) {

        if (canCastImbuedSpell(user)) {
            if (Load.spells(user)
                    .getCastingData()
                    .tryCastImbuedSpell(user)) {
            }
        }

    }

     */

    /*
    public static void crossbow(ItemStack stack, Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> ci) {

        try {
            if (user.level().isClientSide) {
                return;
            }
            if (stack.getItem() instanceof CrossbowItem) {
                if (CrossbowItem.isCharged(stack)) {
                    if (Load.spells(user)
                            .getCastingData()
                            .tryCastImbuedSpell(user)) {
                        //ci.cancel();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

     */
}
