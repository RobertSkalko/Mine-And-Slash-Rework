package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

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


        // todo how do i do max ele resist

        Stat maxstat = new MaxElementalResist(data.GetStat().getElement());

        int max = (int) effect.targetData.getUnit().getStatInCalculation(maxstat).getValue();

        float pene = effect.getPenetration();

        // otherwise phys pene makes this do.. 10x the dmg as its a flat pene value and its used here as a % value
        if (stat.getElement() == Elements.Physical) {
            pene = 0;
        }

        int resist = (int) (data.getValue() + max);

        int defense = (int) (resist - pene);

        float multi = 1 - (defense / 100F);

        effect.data.getNumber(EventData.NUMBER).number *= multi;

        effect.data.setBoolean(EventData.RESISTED_ALREADY, true);

        return effect;

    }


    @Override
    public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
        if (effect.data.getBoolean(EventData.RESISTED_ALREADY)) {
            return false;
        }
        if (effect.GetElement().equals(stat.getElement())) {
            return true;
        }

        return false;
    }

}
