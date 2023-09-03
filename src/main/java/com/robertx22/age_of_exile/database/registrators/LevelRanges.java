package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;

import java.util.Arrays;
import java.util.List;

public class LevelRanges {

    public static LevelRange STARTER_0 = new LevelRange(0 + "", 0, 0.2F);
    public static LevelRange LOW_1 = new LevelRange(1 + "", 0.2F, 0.4F);
    public static LevelRange MIDDLE_2 = new LevelRange(2 + "", 0.4F, 0.6F);
    public static LevelRange HIGH_3 = new LevelRange(3 + "", 0.6F, 0.8F);
    public static LevelRange ENDGAME_4 = new LevelRange(4 + "", 0.8F, 1F);
    public static LevelRange MAX_5 = new LevelRange(5 + "", 1, 1F);


    public static LevelRange START_TO_LOW = new LevelRange("_low", 0F, 0.4F);
    public static LevelRange MID_TO_END = new LevelRange("_end", 0.4F, 1F);
    public static LevelRange FULL = new LevelRange("_full", 0, 1F);

    public static List<LevelRange> allNormal() {
        return Arrays.asList(STARTER_0, LOW_1, MIDDLE_2, HIGH_3, ENDGAME_4);
    }

    public static List<LevelRange> allJewelry() {
        return Arrays.asList(START_TO_LOW, MID_TO_END);
    }
}
