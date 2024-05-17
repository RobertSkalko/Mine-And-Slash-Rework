package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatGuiGroup;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class AilmentProcStat extends Stat {

    Ailment ailment;

    public AilmentProcStat(Ailment ailment) {
        this.ailment = ailment;
        this.is_perc = true;


        this.statEffect = new Effect();
        this.gui_group = StatGuiGroup.AILMENT_PROC_CHANCE;
    }

    private class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.FINAL_DAMAGE;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.targetData.ailments.shatterAccumulated(effect.source, effect.target, ailment, effect.getSpell());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.getElement() != null && effect.getElement() == ailment.element && effect.getAttackType().isHit() && RandomUtils.roll(data.getValue());
        }

    }

    @Override
    public Elements getElement() {
        return ailment.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Procs the accumulated damage of the ailment";
    }

    @Override
    public String locNameForLangFile() {
        String name = "Shatter";
        if (getElement() == Elements.Nature) {
            name = "Shock";
        }// todo if i ever add more, but will be easy to spot
        return name + " Chance";
    }


    @Override
    public String GUID() {
        return ailment.GUID() + "_proc_chance";
    }
}
