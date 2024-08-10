package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayers;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public abstract class BaseDamageIncreaseEffect extends BaseDamageEffect {

    protected BaseDamageIncreaseEffect() {

    }

    @Override
    public final StatPriority GetPriority() {
        return StatPriority.Damage.BEFORE_DAMAGE_LAYERS;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
        effect.getLayer(StatLayers.Offensive.ADDITIVE_DMG, EventData.NUMBER, Side()).add(data.getValue());

        if (stat.getMultiUseType() == Stat.MultiUseType.MULTIPLICATIVE_DAMAGE) {
            effect.addMoreMulti(stat, EventData.NUMBER, data.getMoreStatTypeMulti());
        }

        return effect;
    }

}

