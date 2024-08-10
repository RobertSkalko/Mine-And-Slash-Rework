package com.robertx22.mine_and_slash.event_hooks.damage_hooks;

import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.DmgSourceUtils;
import com.robertx22.mine_and_slash.mixin_ducks.DamageSourceDuck;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;

public class OnPlayerDamageEntityEvent extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        if (event.mob.level().isClientSide) {
            event.canceled = true;
            event.damage = 0;
            return;
        }
        if (event.source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
            return;
        }
        if (DmgSourceUtils.isMyDmgSource(event.source)) {
            return;
        }
        if (event.mob instanceof EnderDragon) {
            return; // todo temp fix
        }
        if (event.source.getEntity() instanceof Player) {
            LivingHurtUtils.tryAttack(new AttackInformation(event, AttackInformation.Mitigation.PRE, event.mob, event.source, event.damage));

            var duck = (DamageSourceDuck) event.source;
            duck.tryOverrideDmgWithMns(event);
        }
    }

}
