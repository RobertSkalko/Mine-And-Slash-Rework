package com.robertx22.mine_and_slash.database.data.perks;

public enum PerkStatus {

    CONNECTED(1, 1), BLOCKED(2, 0.5F), POSSIBLE(4, 1);

    public int order;

    float opacity;

    PerkStatus(int order, float opacity) {
        this.order = order;
        this.opacity = opacity;
    }

    public float getOpacity() {
        return opacity;
    }


}
