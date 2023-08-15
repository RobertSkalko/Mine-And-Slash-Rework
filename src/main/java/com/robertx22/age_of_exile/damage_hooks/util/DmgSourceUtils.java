package com.robertx22.age_of_exile.damage_hooks.util;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import net.minecraft.world.damagesource.DamageSource;

public class DmgSourceUtils {

    public static boolean isMyDmgSource(DamageSource source) {
        return source.is(DamageEvent.DAMAGE_TYPE) || source.getMsgId().equals(DamageEvent.dmgSourceName);
    }

}
