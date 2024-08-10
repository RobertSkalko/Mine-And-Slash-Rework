package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;

public abstract class BaseDamageEffect extends InCodeStatEffect<DamageEvent> {
    public BaseDamageEffect() {
        super(DamageEvent.class);
    }
}