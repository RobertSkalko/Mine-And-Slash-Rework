package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class AllAilmentDamage extends Stat {


    public AllAilmentDamage() {

        this.statEffect = new Effect();
        this.is_perc = true;


    }

    public static AllAilmentDamage getInstance() {
        return AllAilmentDamage.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AllAilmentDamage INSTANCE = new AllAilmentDamage();
    }

    private class Effect extends BaseDamageIncreaseEffect {


        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            boolean bo = !effect.data.getString(EventData.AILMENT).isEmpty();
            return bo;
        }

    }

    @Override
    public Elements getElement() {
        return Elements.NONE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage ailments deals.";
    }

    @Override
    public String locNameForLangFile() {
        return "Ailment Damage";
    }

    @Override
    public String GUID() {
        return "ailment_damage";
    }
}
