package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.uncommon.localization.Words;

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
