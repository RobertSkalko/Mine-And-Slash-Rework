package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.uncommon.effectdatas.ExilePotionEvent;

public abstract class BasePotionEffect extends InCodeStatEffect<ExilePotionEvent> {
    public BasePotionEffect() {
        super(ExilePotionEvent.class);
    }
}