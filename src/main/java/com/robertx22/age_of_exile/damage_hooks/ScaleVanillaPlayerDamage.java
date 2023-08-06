package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.player.Player;

public class ScaleVanillaPlayerDamage extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {
        if (event.mob.level().isClientSide) {
            return;
        }
        if (DmgSourceUtils.isMyDmgSource(event.source)) {
            return;
        }
        if (LivingHurtUtils.isEnviromentalDmg(event.source)) {
            return;
        }
        if (event.source.getEntity() instanceof Player) {
            event.damage = HealthUtils.realToVanilla(event.mob, event.damage);
        }
    }
}

