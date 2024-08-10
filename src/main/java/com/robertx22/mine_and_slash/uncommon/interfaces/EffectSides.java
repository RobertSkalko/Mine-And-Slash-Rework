package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.mine_and_slash.uncommon.localization.Words;

public enum EffectSides {
    Source("source", Words.SOURCE),
    Target("target", Words.TARGET);

    public String id;
    public Words word;


    EffectSides(String id, Words word) {
        this.id = id;
        this.word = word;
    }
}
