package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;

public class MyDamageSource extends EntityDamageSource {

    public Elements element;
    public float realDamage;

    DamageSource source;

    public MyDamageSource(DamageSource s, Entity source, Elements element, float dmg) {
        super(source(s), source);
        this.element = element;
        this.bypassArmor();
        realDamage = dmg;
        this.source = s;

    }

    static String source(DamageSource s) {
        if (s == null) {
            return DamageEvent.dmgSourceName;
        }
        return s.msgId;
    }
}