package com.robertx22.age_of_exile.aoe_data.database.perks.asc;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class SummonerPerks implements ExileRegistryInit {


    public static String SUMMON_1 = "summon1";
    public static String SUMMON_2 = "summon2";
    public static String SUMMON_3 = "summon3";
    public static String SUMMON_4 = "summon4";

    public static String TOTEM_1 = "totem1";
    public static String TOTEM_2 = "totem2";
    public static String TOTEM_3 = "totem3";
    public static String TOTEM_4 = "totem4";

    @Override
    public void registerAll() {

        
        PerkBuilder.ascPoint(SUMMON_1, new OptScaleExactStat(25, Stats.SUMMON_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.ascPoint(SUMMON_2, new OptScaleExactStat(30, Stats.SUMMON_DURATION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(SUMMON_3, new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(SUMMON_4, new OptScaleExactStat(25, Stats.SUMMON_DAMAGE.get(), ModType.MORE));

        PerkBuilder.ascPoint(TOTEM_1, new OptScaleExactStat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.FLAT));
        PerkBuilder.ascPoint(TOTEM_2, new OptScaleExactStat(20, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(TOTEM_3, new OptScaleExactStat(20, Stats.TOTEM_DURATION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(TOTEM_4, new OptScaleExactStat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.totem), ModType.MORE));

    }
}
