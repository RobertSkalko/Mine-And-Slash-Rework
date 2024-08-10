package com.robertx22.mine_and_slash.mixin_ducks;

import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.library_of_exile.events.base.ExileEvents;

public interface DamageSourceDuck {

    public void setMnsDamage(float dmg);

    public float getMnsDamage();

    public void setOriginalHP(float hp);

    public float getOriginalHP();

    public boolean hasMnsDamageOverride();


    default void tryOverrideDmgWithMns(AttackInformation info) {
        if (hasMnsDamageOverride()) {
            info.setAmount(getMnsDamage());

            if (info.getAmount() <= 0) {
                info.setAmount(0);
                info.setCanceled(true);
            } else {
                info.setCanceled(false);
            }
        }
    }

    default void tryOverrideDmgWithMns(ExileEvents.OnDamageEntity event) {
        if (hasMnsDamageOverride()) {
            event.damage = getMnsDamage();

            if (event.damage <= 0) {
                event.damage = 0;
                event.canceled = true;
            } else {
                event.canceled = false;
            }

        }
    }

}
