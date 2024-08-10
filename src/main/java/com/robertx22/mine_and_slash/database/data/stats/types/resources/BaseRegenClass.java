package com.robertx22.mine_and_slash.database.data.stats.types.resources;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseRegenEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public abstract class BaseRegenClass extends Stat {

    @Override
    public String locDescForLangFile() {
        return "Restores this much every few seconds.";
    }

    public BaseRegenClass() {

        this.statEffect = new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public StatPriority GetPriority() {
                return StatPriority.Spell.FIRST;
            }

            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                effect.data.getNumber(EventData.NUMBER).number += data.getValue();
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.data.getResourceType() == getResourceType() && effect.data.getRestoreType() == RestoreType.regen;
            }
        };
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    public abstract ResourceType getResourceType();
}
