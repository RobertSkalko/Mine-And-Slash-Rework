package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.MinMax;

public enum OmenDifficulty {

    EASY(0, new MinMax(1, 1), new MinMax(1, 1), new MinMax(1, 1), new MinMax(0, 0), new MinMax(1, 1)),
    MEDIUM(0.20F, new MinMax(1, 2), new MinMax(1, 2), new MinMax(1, 2), new MinMax(1, 1), new MinMax(1, 2)),
    HARD(0.5F, new MinMax(1, 3), new MinMax(1, 2), new MinMax(1, 2), new MinMax(2, 3), new MinMax(2, 3));

    public float lvl_req;
    public MinMax runed;
    public MinMax normal;
    public MinMax unique;

    public MinMax specificSlots;
    public MinMax affixes;

    OmenDifficulty(float lvl, MinMax normal, MinMax runed, MinMax unique, MinMax specificSlots, MinMax affixes) {
        this.lvl_req = lvl;
        this.runed = runed;
        this.normal = normal;
        this.unique = unique;
        this.specificSlots = specificSlots;
        this.affixes = affixes;
    }
}
