package com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider;

import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;

public class NumberModifier {

    public ModifierType type = ModifierType.SPELL_DAMAGE_EFFECTIVENESS_MULTI;

    public NumberModifier(ModifierType type) {
        this.type = type;
    }

    public enum ModifierType {
        SPELL_DAMAGE_EFFECTIVENESS_MULTI() {
            @Override
            public float modify(EffectEvent event, float value) {
                return event.data.getNumber(EventData.DMG_EFFECTIVENESS, 1).number * value;
            }
        };

        public abstract float modify(EffectEvent event, float value);
    }
}
