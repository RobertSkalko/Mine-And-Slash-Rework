package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class HitDamage extends Stat {


    public HitDamage() {

        this.statEffect = new Effect();
        this.is_perc = true;


    }

    public static HitDamage getInstance() {
        return HitDamage.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final HitDamage INSTANCE = new HitDamage();
    }

    private class Effect extends BaseDamageIncreaseEffect {


        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.data.getString(EventData.AILMENT).isEmpty();
        }

    }

    @Override
    public Elements getElement() {
        return Elements.ALL;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage hits deal.";
    }

    @Override
    public String locNameForLangFile() {
        return "Direct Hit Damage";
    }

    @Override
    public String GUID() {
        return "hit_damage";
    }
}