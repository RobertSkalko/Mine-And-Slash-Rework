package com.robertx22.mine_and_slash.database.data.stats.types.generated;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatGuiGroup;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.database.data.stats.types.ElementalStat;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;
import com.robertx22.mine_and_slash.uncommon.wrappers.MapWrapper;

import java.util.List;

public class ElementalPenetration extends ElementalStat {
    public static MapWrapper<Elements, ElementalPenetration> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalPenetration) x));
        return list;

    }

    public ElementalPenetration(Elements element) {
        super(element);
        this.min = 0;
        this.group = StatGroup.ELEMENTAL;

        this.gui_group = StatGuiGroup.ELE_PENE;
        this.statEffect = new Effect();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {

        return new ElementalPenetration(element);
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_penetration";
    }

    @Override
    public String locDescForLangFile() {
        return "Ignores that much resists, it works as subtraction.";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "ele_pene";
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement()
                .dmgName + " Penetration";
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
            effect.data.getNumber(EventData.PENETRATION).number += data.getValue();
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.GetElement()
                    .equals(stat.getElement()) && !stat.getElement()
                    .equals(Elements.Elemental);
        }

    }

}
