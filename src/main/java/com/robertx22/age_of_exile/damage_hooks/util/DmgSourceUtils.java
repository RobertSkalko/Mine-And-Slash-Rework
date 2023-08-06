package com.robertx22.age_of_exile.damage_hooks.util;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;

public class DmgSourceUtils {

    public static boolean isMyDmgSource(DamageSource source) {
        return source.is(DamageTypes.FELL_OUT_OF_WORLD) || source.getMsgId().equals(DamageEvent.dmgSourceName);
    }

}
