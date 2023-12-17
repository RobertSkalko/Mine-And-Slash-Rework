package com.robertx22.age_of_exile.aoe_data.database.perks.asc;

import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class PaladinPerks implements ExileRegistryInit {


    public static String PALADIN_1 = "paladin1";
    public static String PALADIN_2 = "paladin2";
    public static String PALADIN_3 = "paladin3";
    public static String PALADIN_4 = "paladin4";

    public static String GUARDIAN_1 = "guardian1";
    public static String GUARDIAN_2 = "guardian2";
    public static String GUARDIAN_3 = "guardian3";
    public static String GUARDIAN_4 = "guardian4";

    public static String WARRIOR_1 = "warrior1";
    public static String WARRIOR_2 = "warrior2";
    public static String WARRIOR_3 = "warrior3";
    public static String WARRIOR_4 = "warrior4";

    @Override
    public void registerAll() {

        PerkBuilder.ascPoint(PALADIN_1, new OptScaleExactStat(25, Stats.HEAL_STRENGTH.get(), ModType.FLAT), new OptScaleExactStat(10, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT));
        PerkBuilder.ascPoint(PALADIN_2, new OptScaleExactStat(10, Health.getInstance(), ModType.PERCENT), new OptScaleExactStat(15, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT));
        PerkBuilder.ascPoint(PALADIN_3, new OptScaleExactStat(10, Stats.HEALING_RECEIVED.get(), ModType.PERCENT), new OptScaleExactStat(20, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT));
        PerkBuilder.ascPoint(PALADIN_4, new OptScaleExactStat(30, Stats.HEAL_STRENGTH.get(), ModType.FLAT), new OptScaleExactStat(30, DatapackStats.HEAL_TO_SPELL_DMG, ModType.FLAT));

        PerkBuilder.ascPoint(GUARDIAN_1, new OptScaleExactStat(15, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.ascPoint(GUARDIAN_2, new OptScaleExactStat(20, Armor.getInstance(), ModType.PERCENT));
        PerkBuilder.ascPoint(GUARDIAN_3, new OptScaleExactStat(25, Health.getInstance(), ModType.PERCENT));
        PerkBuilder.ascPoint(GUARDIAN_4, new OptScaleExactStat(25, Armor.getInstance(), ModType.MORE));

        PerkBuilder.ascPoint(WARRIOR_1, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT));
        PerkBuilder.ascPoint(WARRIOR_2, new OptScaleExactStat(25, ArmorPenetration.getInstance(), ModType.PERCENT));
        PerkBuilder.ascPoint(WARRIOR_3, new OptScaleExactStat(30, Stats.CRIT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.ascPoint(WARRIOR_4, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE));

    }
}
