package com.robertx22.age_of_exile.aoe_data.database.perks;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class AscPerks implements ExileRegistryInit {
    public static String TEST = "test";

    public static String FIRE_NOVICE = "fire_novice";
    public static String COLD_NOVICE = "cold_novice";
    public static String LIGHTNING_NOVICE = "lightning_novice";

    public static String FIRE_APPRENTICE = "fire_apprentice";
    public static String COLD_APPRENTICE = "cold_apprentice";
    public static String LIGHTNING_APPRENTICE = "lightning_apprentice";

    public static String FIRE_EXPERT = "fire_expert";
    public static String COLD_EXPERT = "cold_expert";
    public static String LIGHTNING_EXPERT = "lightning_expert";

    public static String FIRE_MASTER = "fire_master";
    public static String COLD_MASTER = "cold_master";
    public static String LIGHTNING_MASTER = "lightning_master";


    @Override
    public void registerAll() {

        PerkBuilder.stat(TEST, new OptScaleExactStat(100, DatapackStats.STR, ModType.FLAT));

        PerkBuilder.ascPoint(FIRE_NOVICE, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT));
        PerkBuilder.ascPoint(COLD_NOVICE, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT));
        PerkBuilder.ascPoint(LIGHTNING_NOVICE, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning), ModType.FLAT));

        PerkBuilder.ascPoint(FIRE_APPRENTICE, new OptScaleExactStat(10, new AilmentChance(Ailments.BURN), ModType.FLAT));
        PerkBuilder.ascPoint(COLD_APPRENTICE, new OptScaleExactStat(10, new AilmentChance(Ailments.FREEZE), ModType.FLAT));
        PerkBuilder.ascPoint(LIGHTNING_APPRENTICE, new OptScaleExactStat(10, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT));

        PerkBuilder.ascPoint(FIRE_EXPERT, new OptScaleExactStat(15, new ElementalPenetration(Elements.Fire), ModType.FLAT));
        PerkBuilder.ascPoint(COLD_EXPERT, new OptScaleExactStat(15, new ElementalPenetration(Elements.Cold), ModType.FLAT));
        PerkBuilder.ascPoint(LIGHTNING_EXPERT, new OptScaleExactStat(15, new ElementalPenetration(Elements.Lightning), ModType.FLAT));

        PerkBuilder.ascPoint(FIRE_MASTER, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.MORE));
        PerkBuilder.ascPoint(COLD_MASTER, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE));
        PerkBuilder.ascPoint(LIGHTNING_MASTER, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning), ModType.MORE));

    }
}
