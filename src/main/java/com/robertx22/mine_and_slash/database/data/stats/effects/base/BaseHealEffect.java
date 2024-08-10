package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.uncommon.effectdatas.RestoreResourceEvent;

public abstract class BaseHealEffect extends InCodeStatEffect<RestoreResourceEvent> {
    public BaseHealEffect() {
        super(RestoreResourceEvent.class);
    }
}
