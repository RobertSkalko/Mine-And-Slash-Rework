package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.MinMax;

public class OmenDifficulty {


    public MinMax runed;
    public MinMax normal;
    public MinMax unique;

    public MinMax specific_slots;
    public MinMax affixes;

    public float stat_multi = 1;

    public OmenDifficulty(MinMax runed, MinMax normal, MinMax unique, MinMax specific_slots, MinMax affixes, float stat_multi) {
        this.runed = runed;
        this.normal = normal;
        this.unique = unique;
        this.specific_slots = specific_slots;
        this.affixes = affixes;
        this.stat_multi = stat_multi;
    }


}
