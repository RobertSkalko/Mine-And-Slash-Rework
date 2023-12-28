package com.robertx22.age_of_exile.aoe_data.database.perks.asc;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class RangerPerks implements ExileRegistryInit {


    public static String ARCHERY_1 = "archery1";
    public static String ARCHERY_2 = "archery2";
    public static String ARCHERY_3 = "archery3";
    public static String ARCHERY_4 = "archery4";

    public static String TRAP_1 = "trap1";
    public static String TRAP_2 = "trap2";
    public static String TRAP_3 = "trap3";
    public static String TRAP_4 = "trap4";


    public static String BEAST_1 = "beast1";
    public static String BEAST_2 = "beast2";
    public static String BEAST_3 = "beast3";
    public static String BEAST_4 = "beast4";

    @Override
    public void registerAll() {

        PerkBuilder.ascPoint(ARCHERY_1, new OptScaleExactStat(25, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_2, new OptScaleExactStat(20, Stats.CRIT_CHANCE.get(), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_3, new OptScaleExactStat(25, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(ARCHERY_4, new OptScaleExactStat(25, Stats.STYLE_DAMAGE.get(PlayStyle.DEX), ModType.MORE));

        PerkBuilder.ascPoint(TRAP_1, new OptScaleExactStat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap), ModType.FLAT));
        PerkBuilder.ascPoint(TRAP_2, new OptScaleExactStat(25, Stats.COOLDOWN_REDUCTION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(TRAP_3, new OptScaleExactStat(25, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTags.trap), ModType.FLAT));
        PerkBuilder.ascPoint(TRAP_4, new OptScaleExactStat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.trap), ModType.MORE));

        PerkBuilder.ascPoint(BEAST_1, new OptScaleExactStat(1, Stats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT));
        PerkBuilder.ascPoint(BEAST_2, new OptScaleExactStat(30, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.beast), ModType.FLAT));
        PerkBuilder.ascPoint(BEAST_3, new OptScaleExactStat(1, Stats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT), new OptScaleExactStat(25, Stats.SUMMON_DURATION.get(), ModType.FLAT));
        PerkBuilder.ascPoint(BEAST_4, new OptScaleExactStat(30, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTags.beast), ModType.MORE));

    }
}
