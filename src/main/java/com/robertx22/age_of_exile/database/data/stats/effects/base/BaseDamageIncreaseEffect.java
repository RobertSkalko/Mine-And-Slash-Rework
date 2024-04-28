package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public abstract class BaseDamageIncreaseEffect extends BaseDamageEffect {

    protected BaseDamageIncreaseEffect() {

    }

    @Override
    public final StatPriority GetPriority() {
        return StatPriority.Damage.BEFORE_DAMAGE_LAYERS;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
        effect.increaseByPercent(data.getValue());

        effect.data.getNumber(EventData.NUMBER).number = effect.data.getNumber() * data.getMoreStatTypeMulti();

        return effect;
    }

}

