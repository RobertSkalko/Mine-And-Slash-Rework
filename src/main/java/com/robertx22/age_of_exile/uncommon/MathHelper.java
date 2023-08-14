package com.robertx22.age_of_exile.uncommon;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.lenientFormat;

public class MathHelper {

    public static int clamp(int value, int min, int max) {
        checkArgument(min <= max, "min (%s) must be less than or equal to max (%s)", min, max);
        return java.lang.Math.min(java.lang.Math.max(value, min), max);
    }

    public static float clamp(float value, float min, float max) {
        // avoid auto-boxing by not using Preconditions.checkArgument(); see Guava issue 3984
        // Reject NaN by testing for the good case (min <= max) instead of the bad (min > max).
        if (min <= max) {
            return java.lang.Math.min(java.lang.Math.max(value, min), max);
        }
        throw new IllegalArgumentException(
                lenientFormat("min (%s) must be less than or equal to max (%s)", min, max));
    }
}
