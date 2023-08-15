package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

import java.util.Arrays;
import java.util.List;

public interface IRarity<R extends Rarity> {


    public static int TOTAL_GEAR_RARITIES = 6;

    public String getRarityId();

    public R getRarity();

    public default boolean isUnique() {
        return this.getRarityId()
                .equals(UNIQUE_ID);
    }

    String COMMON_ID = "common";
    String UNCOMMON = "uncommon";
    String RARE_ID = "rare";
    String EPIC_ID = "epic";
    String LEGENDARY_ID = "legendary";
    String MYTHIC_ID = "mythic";

    public static List<String> NORMAL_GEAR_RARITIES = Arrays.asList(COMMON_ID, UNCOMMON, RARE_ID, EPIC_ID, LEGENDARY_ID, MYTHIC_ID);


    String BOSS_ID = "boss";
    String ELITE_ID = "elite";
    String CHAMPION_ID = "champ";

    String UNIQUE_ID = "unique";
    String RUNEWORD_ID = "runeword";

}
