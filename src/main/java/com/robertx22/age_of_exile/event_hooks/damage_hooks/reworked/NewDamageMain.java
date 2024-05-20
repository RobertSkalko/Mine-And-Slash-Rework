package com.robertx22.age_of_exile.event_hooks.damage_hooks.reworked;

import com.robertx22.age_of_exile.event_hooks.damage_hooks.OnNonPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.OnPlayerDamageEntityEvent;
import com.robertx22.age_of_exile.mixin_ducks.DamageSourceDuck;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public class NewDamageMain {

    public static void init() {

        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnNonPlayerDamageEntityEvent());
        ExileEvents.DAMAGE_BEFORE_CALC.register(new OnPlayerDamageEntityEvent());

        ExileEvents.DAMAGE_BEFORE_CALC.register(new EventConsumer<>() {
            @Override
            public void accept(ExileEvents.OnDamageEntity event) {
                if (event.source != null) {
                    var duck = (DamageSourceDuck) event.source;
                    duck.setOriginalHP(event.mob.getHealth());
                }
            }
        });

        // todo this isnt last sometimes, and other mods might modify it..
        ExileEvents.DAMAGE_AFTER_CALC.register(new EventConsumer<>() {
            @Override
            public void accept(ExileEvents.OnDamageEntity event) {
                if (event.source != null) {
                    var duck = (DamageSourceDuck) event.source;
                    duck.tryOverrideDmgWithMns(event);
                }
            }
        });
    }
}
