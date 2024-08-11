package com.robertx22.mine_and_slash.database.data;

import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.mine_and_slash.uncommon.MathHelper;

public class MinMax {

    public int min;
    public int max;

    public MinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public boolean isInRange(int num) {
        if (num >= min) {
            if (num <= max) {
                return true;
            }
        }
        return false;

    }

    public int capNumber(int num) {
        return MathHelper.clamp(num, min, max);
    }

    public int random() {
        return RandomUtils.RandomRange(min, max);
    }

}
