package com.robertx22.mine_and_slash.database.data.stats.effects.game_changers;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class BloodUserEffect extends InCodeStatEffect<SpendResourceEvent> {

    private BloodUserEffect() {
        super(SpendResourceEvent.class);
    }

    public static BloodUserEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatPriority GetPriority() {
        return StatPriority.Spell.FIRST;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public SpendResourceEvent activate(SpendResourceEvent effect, StatData data, Stat stat) {
        effect.data.setString(EventData.RESOURCE_TYPE, ResourceType.blood.name());
        return effect;
    }

    @Override
    public boolean canActivate(SpendResourceEvent effect, StatData data, Stat stat) {
        if (effect.data.getResourceType() == ResourceType.mana) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final BloodUserEffect INSTANCE = new BloodUserEffect();
    }
}