package com.robertx22.mine_and_slash.aoe_data.database.unique_gears;

public enum UniqueRarityTier {
    COMMON(1000),
    RARE(200),
    UBER(50);
    public int weight;

    UniqueRarityTier(int weight) {
        this.weight = weight;
    }
}
