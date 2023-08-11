package com.robertx22.age_of_exile.aoe_data.database.perks.asc;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class RangerPerks implements ExileRegistryInit {


    public static String ARCHERY_1 = "archery1";
    public static String ARCHERY_2 = "archery2";
    public static String ARCHERY_3 = "archery3";
    public static String ARCHERY_4 = "archery4";


    @Override
    public void registerAll() {

        PerkBuilder.ascPoint(ARCHERY_1, new OptScaleExactStat(25, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_2, new OptScaleExactStat(20, Stats.CRIT_CHANCE.get(), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_3, new OptScaleExactStat(25, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_4, new OptScaleExactStat(25, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.MORE));
    }
}
