package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.LivingHurtUtils;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;

public class OnHurtEvent {

    public static float onHurtEvent(AttackInformation event) {

        if (event.getTargetEntity().level().isClientSide) {
            return event.getAmount();
        }

        try {


            // order matters here
            LivingHurtUtils.tryAttack(event);
            // order matters here

            event.event.damage *= ServerContainer.get().PLAYER_VANILLA_DMG_MULTI.get();
            //event.event.canceled = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return event.getAmount();

    }

}
