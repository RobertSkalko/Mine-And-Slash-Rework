package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.List;

public class BonusPhysicalAsElemental extends ElementalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        return list;

    }

    public BonusPhysicalAsElemental(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;

        this.statEffect = new Effect();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new BonusPhysicalAsElemental(element);
    }

    @Override
    public String GUID() {
        return "plus_phys_to_" + this.getElement().guidName;
    }

    @Override
    public String locDescForLangFile() {
        return "Grants % of physical attack damage as extra elemental damage";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "bonus_phys_to_ele";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    private class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.BEFORE_DAMAGE_LAYERS;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            float dmg = effect.data.getNumber() * data.getValue() / 100F;
            effect.addBonusEleDmg(stat.getElement(), dmg);
            // effect.data.getNumber(EventData.NUMBER).number -= dmg;
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.GetElement() == Elements.Physical && effect.getAttackType().equals(AttackType.hit);
        }

    }

    @Override
    public String locNameForLangFile() {
        return "Physical as Extra " + this.getElement().dmgName + " Damage";
    }

}