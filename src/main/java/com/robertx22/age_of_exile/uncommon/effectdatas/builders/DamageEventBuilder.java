package com.robertx22.age_of_exile.uncommon.effectdatas.builders;

import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ErrorUtils;

public class DamageEventBuilder extends EventBuilder<DamageEvent> {

    boolean isSetup = false;

    public DamageEventBuilder setupDamage(AttackType effectType, WeaponTypes weptype, PlayStyle style) {
        this.isSetup = true;
        event.data.setString(EventData.STYLE, style.id);
        event.data.setString(EventData.WEAPON_TYPE, weptype.id);
        event.data.setString(EventData.ATTACK_STYLE, style.id);
        event.data.setString(EventData.ATTACK_TYPE, effectType.name());
        return this;
    }

    public DamageEventBuilder setIsBasicAttack() {
        event.data.setBoolean(EventData.IS_BASIC_ATTACK, true);
        return this;
    }


    @Override
    protected void buildCheck() {
        ErrorUtils.ifFalse(isSetup);
    }

}
