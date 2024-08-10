package com.robertx22.mine_and_slash.database.data.game_balance_config;

public class PlayerPointsConfig {

    public PlayerPointsType type;
    public int base_points = 0;
    public float points_per_lvl = 1;
    public int max_bonus_points;
    public int max_total_points;

    public PlayerPointsConfig(PlayerPointsType type, int base_points, float points_per_lvl, int max_bonus_points, int max_total_points) {
        this.type = type;
        this.base_points = base_points;
        this.points_per_lvl = points_per_lvl;
        this.max_bonus_points = max_bonus_points;
        this.max_total_points = max_total_points;
    }

}
