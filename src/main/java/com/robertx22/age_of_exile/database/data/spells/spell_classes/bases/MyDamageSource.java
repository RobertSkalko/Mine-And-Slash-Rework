package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;


// todo this needs a rework?
public class MyDamageSource extends DamageSource {
    public MyDamageSource(Holder<DamageType> pType) {
        super(pType);
    }

/*
    DamageSource source;

    public MyDamageSource(DamageSource s, Entity source, float dmg) {
        super(source(s), source);
        this.bypassArmor();
        this.source = s;

    }

    static String source(DamageSource s) {
        if (s == null) {
            return DamageEvent.dmgSourceName;
        }
        return s.msgId;
    }

 */
}