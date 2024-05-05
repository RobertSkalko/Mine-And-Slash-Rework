package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.layers.StatLayers;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class DamageShield extends Stat {

    private DamageShield() {
        this.scaling = StatScaling.NORMAL;
        this.statEffect = new Effect();

    }

    public static DamageShield getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "damage_shield";
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Decreases that amount of damage from every attack.";
    }

    @Override
    public String locNameForLangFile() {
        return "Damage Shield";
    }

    private static class Effect extends BaseDamageEffect {
        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.DAMAGE_ABSORBED_BY_SHIELD;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            effect.getLayer(StatLayers.Defensive.FLAT_DAMAGE_REDUCTION, EventData.NUMBER, Side()).reduce(data.getValue());
            //      effect.data.getNumber(EventData.NUMBER).number -= data.getValue();
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return true;
        }

    }

    private static class SingletonHolder {
        private static final DamageShield INSTANCE = new DamageShield();
    }
}
