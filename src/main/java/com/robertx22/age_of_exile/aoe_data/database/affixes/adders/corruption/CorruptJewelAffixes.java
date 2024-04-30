package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.corruption;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.EffectStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class CorruptJewelAffixes {
    static String PREFIX = "jewel_corrupt_";

    public static void init() {


        for (EffectCtx curse : ModEffects.getCurses()) {
            var stat = EffectStats.EFFECT_IMMUNITY.get(curse);
            AffixBuilder.Normal(PREFIX + stat.GUID())
                    .stat(stat.mod(1, 1))
                    .JewelCorruption()
                    .Weight(1000)
                    .Build();
        }


        of(AuraCapacity.getInstance(), 1, 3).Build();
        of(AuraEffect.getInstance(), 1, 3).Build();

        of(OffenseStats.CRIT_DAMAGE.get(), 2, 5).Build();
        of(OffenseStats.CRIT_CHANCE.get(), 1, 2).Build();

        of(SpellChangeStats.CAST_SPEED.get(), 1, 3).Build();
        of(SpellChangeStats.COOLDOWN_REDUCTION.get(), 1, 3).Build();

        for (Elements ele : Elements.getAllSingleElemental()) {
            if (ele != Elements.Physical) {
                of(new MaxElementalResist(ele), 1, 2).Build();
            }
        }

    }

    static AffixBuilder of(Stat stat, int v1, int v2) {

        return AffixBuilder.Normal(PREFIX + stat.GUID())
                .stat(stat.mod(1, 1))
                .JewelCorruption()
                .Weight(1000);
    }
}
