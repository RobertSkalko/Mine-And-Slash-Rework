package com.robertx22.mine_and_slash.database.data.stats.types.ailment;

import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailment;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatGuiGroup;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class AilmentDamage extends Stat {

    Ailment ailment;

    public AilmentDamage(Ailment ailment) {
        this.ailment = ailment;
        this.is_perc = true;
        this.statEffect = new Effect();
        
        this.gui_group = StatGuiGroup.AILMENT_DAMAGE;
    }

    private class Effect extends BaseDamageIncreaseEffect {


        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            boolean bo = effect.data.getString(EventData.AILMENT).equals(ailment.GUID());
            return bo;
        }

    }

    @Override
    public Elements getElement() {
        return ailment.element;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases damage the ailment deals.";
    }

    @Override
    public String locNameForLangFile() {
        return ailment.locNameForLangFile() + " Damage";
    }

    @Override
    public String GUID() {
        return ailment.GUID() + "_damage";
    }
}
