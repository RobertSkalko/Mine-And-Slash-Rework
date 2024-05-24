package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public interface IUsableStat {

    /**
     * 0.75 means 75% will be maximum value
     */
    public float getMaxMulti();

    /**
     * Used to get usable value. So 5000 armor turns into 50% armor reduction
     */
    public float valueNeededToReachMaximumPercentAtLevelOne();

    public default float scaledvalueNeededToReachMaximumPercentAtLevelOne(int lvl) {
        Stat stat = (Stat) this;
        return stat.scale(ModType.FLAT, valueNeededToReachMaximumPercentAtLevelOne(), lvl);
    }

    default void logUsableAmountTests() {

        Stat stat = (Stat) this;

        int val = 50;

        for (int i = 0; i < 100; i++) {

            float multi = getUsableValue(val, 1);

            System.out.print("\n For " + val + " " + stat.GUID() + " usable value is: " + multi);

            val += 50;
        }

    }

    public default float getUsableValue(int value, int lvl) {

        if (this instanceof Stat stat) {
            value = Math.max(0, value);

            float base = scaledvalueNeededToReachMaximumPercentAtLevelOne(lvl);

            float val = value / (value + base);

            return MathHelper.clamp(val, 0F, getMaxMulti());
        }
        return 0;
    }
}
