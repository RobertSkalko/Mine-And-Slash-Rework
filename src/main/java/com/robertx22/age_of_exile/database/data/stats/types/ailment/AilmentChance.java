package com.robertx22.age_of_exile.database.data.stats.types.ailment;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatGuiGroup;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;

public class AilmentChance extends Stat {

    Ailment ailment;

    public AilmentChance(Ailment ailment) {
        this.ailment = ailment;
        this.is_perc = true;
        this.statEffect = new Effect();
        this.min = 0;
        this.max = 100;

        this.gui_group = StatGuiGroup.AILMENT_CHANCE;
    }

    private class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Spell.FIRST;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            // we take the original or base damage of the attack so we don't double dip
            float dmg = effect.data.getOriginalNumber(EventData.NUMBER).number;

            // todo will probably have to tweak this
            var event = EventBuilder.ofDamage(effect.source, effect.target, dmg).setupDamage(AttackType.dot, WeaponTypes.none, PlayStyle.INT).set(x -> {
                x.disableActivation = true; // we dont actually want to do the dmg now
                x.setElement(ailment.element);
                x.setisAilmentDamage(ailment);
                if (effect.isSpell()) {
                    x.data.setString(EventData.SPELL, effect.data.getString(EventData.SPELL));
                }
                x.calcTargetEffects = false;
            }).build();

            event.Activate();

            Load.Unit(effect.target).ailments.onAilmentCausingDamage(effect.source, effect.target, ailment, event.data.getNumber());

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
        return "Chance to Cause the Ailment on hit. Maximum chance is 100%. " + ailment.locDescForLangFile();
    }

    @Override
    public String locNameForLangFile() {
        return ailment.locNameForLangFile() + " Chance";
    }

    @Override
    public String GUID() {
        return ailment.GUID() + "_chance";
    }
}
