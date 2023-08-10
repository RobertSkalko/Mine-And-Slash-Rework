package com.robertx22.age_of_exile.database.data.level_ranges;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.util.Mth;

public class LevelRange {
    public static LevelRange SERIALIZER = new LevelRange(0, 0);

    private float start;
    private float end;
    public transient String id_suffix;

    public LevelRange(String id_suffix, float start, float end) {
        this.start = start;
        this.end = end;
        this.id_suffix = id_suffix;
    }

    private LevelRange(float start, float end) {
        this.start = start;
        this.end = end;
    }

    public int getMiddleLevel() {
        return (getMinLevel() + getMaxLevel()) / 2;
    }

    public int randomFromRange() {
        return RandomUtils.RandomRange(getMinLevel(), getMaxLevel());
    }

    public int getMinLevel() {
        return Mth.clamp((int) (start * GameBalanceConfig.get().MAX_LEVEL), 0, Integer.MAX_VALUE);
    }

    public int getMaxLevel() {
        return (int) (end * GameBalanceConfig.get().MAX_LEVEL);
    }

    public float getEndPercent() {
        return end;
    }

    public float getStartPercent() {
        return start;
    }

    public boolean isLevelInRange(int lvl) {
        return lvl >= getMinLevel() && lvl <= getMaxLevel();
    }


}
