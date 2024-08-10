package com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class MagicShieldHeal extends Stat {

    private MagicShieldHeal() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.Misc;

        //this.is_long = true;

        this.statEffect = new Effect();

    }


    public static MagicShieldHeal getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "A % of your healing goes to your magic shield too.";
    }

    @Override
    public String GUID() {
        return "magic_shield_heal";
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield Healing";
    }


    private static class SingletonHolder {
        private static final MagicShieldHeal INSTANCE = new MagicShieldHeal();
    }

    public class Effect extends InCodeStatEffect<RestoreResourceEvent> {

        private Effect() {
            super(RestoreResourceEvent.class);
        }


        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.DAMAGE_LAYERS;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Source;
        }


        @Override
        public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
            float num = effect.data.getOriginalNumber(EventData.NUMBER).number * data.getValue() / 100F;
            // we get the original so the scaling only happens for magic shield, not twice
            RestoreResourceEvent restore = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.magic_shield, effect.data.getRestoreType(), num)
                    .build();
            restore.Activate();
            return effect;
        }

        @Override
        public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
            if (effect.data.getResourceType() == ResourceType.health) {
                return true;
            }
            return false;
        }

    }
}

