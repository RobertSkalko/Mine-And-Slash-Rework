package com.robertx22.age_of_exile.aoe_data.database.perks.asc;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.perks.PerkBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDamage;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class WarlockPerks implements ExileRegistryInit {


    public static String POISON_1 = "poison1";
    public static String POISON_2 = "poison2";
    public static String POISON_3 = "poison3";
    public static String POISON_4 = "poison4";


    public static String CHAOS_1 = "chaos1";
    public static String CHAOS_2 = "chaos2";
    public static String CHAOS_3 = "chaos3";
    public static String CHAOS_4 = "chaos4";

    public static String CURSE_1 = "curse1";
    public static String CURSE_2 = "curse2";
    public static String CURSE_3 = "curse3";
    public static String CURSE_4 = "curse4";


    @Override
    public void registerAll() {

        PerkBuilder.ascPoint(POISON_1, new OptScaleExactStat(20, new AilmentChance(Ailments.POISON), ModType.FLAT));
        PerkBuilder.ascPoint(POISON_2, new OptScaleExactStat(25, new AilmentDamage(Ailments.POISON), ModType.FLAT));
        PerkBuilder.ascPoint(POISON_3, new OptScaleExactStat(30, new AilmentDuration(Ailments.POISON), ModType.FLAT));
        PerkBuilder.ascPoint(POISON_4, new OptScaleExactStat(25, new AilmentDamage(Ailments.POISON), ModType.MORE));

        PerkBuilder.ascPoint(CHAOS_1, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.FLAT));
        PerkBuilder.ascPoint(CHAOS_2, new OptScaleExactStat(15, new ElementalPenetration(Elements.Chaos), ModType.FLAT));
        PerkBuilder.ascPoint(CHAOS_3, new OptScaleExactStat(50, new ElementalResist(Elements.Chaos), ModType.FLAT));
        PerkBuilder.ascPoint(CHAOS_4, new OptScaleExactStat(25, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.FLAT));

        PerkBuilder.ascPoint(CURSE_1, new OptScaleExactStat(30, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.FLAT));
        PerkBuilder.ascPoint(CURSE_2, new OptScaleExactStat(50, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.curse), ModType.FLAT));
        PerkBuilder.ascPoint(CURSE_3, new OptScaleExactStat(20, Stats.DOT_DAMAGE.get(), ModType.FLAT));
        PerkBuilder.ascPoint(CURSE_4, new OptScaleExactStat(25, Stats.EFFECT_OF_BUFFS_GIVEN_PER_EFFECT_TAG.get(EffectTags.curse), ModType.FLAT), new OptScaleExactStat(10, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos), ModType.MORE));


    }
}
