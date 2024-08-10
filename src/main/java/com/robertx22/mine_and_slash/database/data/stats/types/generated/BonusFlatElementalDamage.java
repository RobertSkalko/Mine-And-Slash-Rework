package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.number_provider.NumberModifier;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;

public class BonusFlatElementalDamage extends ElementalStat {

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        Elements.getAllSingle()
                .forEach(x -> list.add(newGeneratedInstance(x)));
        return list;
    }

    public BonusFlatElementalDamage(Elements element) {
        super(element);
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.ELEMENTAL;
        this.statEffect = new Effect();

        this.format = element.format.getName();
        this.icon = element.icon;

    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new BonusFlatElementalDamage(element);
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "ele_added_dmg";
    }

    @Override
    public String locNameForLangFile() {
        return "Added " + getElement().dmgName + " Damage";
    }

    @Override
    public String locDescForLangFile() {
        return "Adds x element damage on hit.  It's multiplied by damage effectiveness of the hit";
    }

    @Override
    public String GUID() {
        return "flat_" + this.getElement().guidName + "_added_damage";
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
            if (stat.getElement() == effect.GetElement()) {
                effect.getLayer(StatLayers.Offensive.FLAT_DAMAGE, EventData.NUMBER, Side()).add(data.getValue());
            } else {
                effect.addBonusEleDmg(stat.getElement(), NumberModifier.ModifierType.SPELL_DAMAGE_EFFECTIVENESS_MULTI.modify(effect, data.getValue()));
            }
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.data.getAttackType().isHit() && !effect.data.getBoolean(EventData.IS_BONUS_ELEMENT_DAMAGE);
        }

    }

}
