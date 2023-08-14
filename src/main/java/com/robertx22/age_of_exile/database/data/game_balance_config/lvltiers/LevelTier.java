package com.robertx22.age_of_exile.database.data.game_balance_config.lvltiers;

public class LevelTier {

    public int tier;
    public int min;
    public int max;


    public LevelTier(int tier, int min, int max) {
        this.tier = tier;
        this.min = min;
        this.max = max;
    }
}
