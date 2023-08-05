package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect.Priority;

public class ElementalResistEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.Fifth.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

        if (effect.data.getBoolean(EventData.IGNORE_RESIST)) {
            return effect;
        }

        float pene = effect.getPenetration();

        int resist = (int) data.getValue();

        int defense = (int) (resist - pene);


        float multi = 1 - (defense / 100F);

        effect.data.getNumber(EventData.NUMBER).number *= multi;

/*
        IUsableStat eleresist = (IUsableStat) stat;

        float EffectiveArmor = eleresist.getUsableValue((int) (data.getValue() - pene), effect.sourceData.getLevel());

        EffectiveArmor = MathHelper.clamp(EffectiveArmor, 0, 1);

        effect.data.getNumber(EventData.NUMBER).number -= EffectiveArmor * effect.data.getNumber();


 */
        return effect;

    }

    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        if (effect.GetElement() != Elements.Physical) {
            if (effect.GetElement()
                    .equals(stat.getElement())) {
                return true;
            }
        }
        return false;
    }

}
