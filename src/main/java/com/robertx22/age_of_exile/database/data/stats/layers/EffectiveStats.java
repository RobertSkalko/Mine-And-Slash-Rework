package com.robertx22.age_of_exile.database.data.stats.layers;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;

public class EffectiveStats {


    public static IUsableStat ARMOR = new IUsableStat() {
        @Override
        public float getMaxMulti() {
            return 0.9F;
        }

        @Override
        public float valueNeededToReachMaximumPercentAtLevelOne() {
            return 20;
        }
    };

    public static IUsableStat DODGE = new IUsableStat() {
        @Override
        public float getMaxMulti() {
            return 0.8F;
        }

        @Override
        public float valueNeededToReachMaximumPercentAtLevelOne() {
            return 15;
        }
    };

}
