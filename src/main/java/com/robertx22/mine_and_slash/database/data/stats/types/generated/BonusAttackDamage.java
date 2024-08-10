package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;

public class BonusAttackDamage extends ElementalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {

        List<Stat> list = new ArrayList<>();
        Elements.getAllSingle()
                .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

    public BonusAttackDamage(Elements element) {
        super(element);
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.ELEMENTAL;
        this.statEffect = new Effect();

        this.format = element.format.getName();
        this.icon = element.icon;

    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new BonusAttackDamage(element);
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "ele_atk_dmg";
    }

    @Override
    public String locNameForLangFile() {
        return getElement().dmgName + " DMG on Basic ATK";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds x element damage on basic attacks only";
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_weapon_damage";
    }

    private static class Effect extends BaseDamageEffect {

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
            effect.addBonusEleDmg(stat.getElement(), data.getValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.data.isBasicAttack();
        }

    }

}
