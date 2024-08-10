package com.robertx22.mine_and_slash.database.data.stats.effects.game_changers;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.InCodeStatEffect;
import com.robertx22.mine_and_slash.database.data.stats.priority.StatPriority;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EventBuilder;
import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.mine_and_slash.uncommon.interfaces.EffectSides;

public class HealthRestorationToBloodEffect extends InCodeStatEffect<RestoreResourceEvent> {

    private HealthRestorationToBloodEffect() {
        super(RestoreResourceEvent.class);
    }

    public static HealthRestorationToBloodEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public StatPriority GetPriority() {
        return StatPriority.Damage.FINAL_DAMAGE;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {

        float bloodrestored = effect.data.getNumber() * data.getValue() / 100F;

        RestoreResourceEvent restore = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.blood, RestoreType.regen, bloodrestored)
                .build();

        restore.Activate();
        return effect;
    }

    @Override
    public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {

        if (effect.data.isSpellEffect()) {
            return false;
        }
        if (effect.data.getResourceType() == ResourceType.health) {
            return true;
        }
        return false;
    }

    private static class SingletonHolder {
        private static final HealthRestorationToBloodEffect INSTANCE = new HealthRestorationToBloodEffect();
    }
}