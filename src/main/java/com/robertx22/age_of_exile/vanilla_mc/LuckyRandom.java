package com.robertx22.age_of_exile.vanilla_mc;

import com.robertx22.library_of_exile.utils.RandomUtils;

public class LuckyRandom {

    public enum LuckyUnlucky {
        LUCKY, UNLUCKY;
    }

    public static int randomInt(int from, int to, LuckyUnlucky lucky, int times) {

        int num = RandomUtils.RandomRange(from, to);

        for (int i = 0; i < times - 1; i++) {

            int ran = RandomUtils.RandomRange(from, to);

            if (lucky == LuckyUnlucky.LUCKY) {
                if (ran > num) {
                    num = ran;
                }
            } else {
                if (num > ran) {
                    num = ran;
                }
            }

        }

        return num;
    }
}
