package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.corruption;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.tags.imp.SlotTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class CorruptionAffixes {
    static String PREFIX = "gear_corrupt";

    public static void init() {

        of(AuraCapacity.getInstance(), 1, 3, SlotTags.jewelry_family).Build();
        of(AuraEffect.getInstance(), 1, 3, SlotTags.jewelry_family).Build();

        of(OffenseStats.CRIT_DAMAGE.get(), 3, 15, SlotTags.weapon_family).Build();
        of(OffenseStats.CRIT_CHANCE.get(), 1, 3, SlotTags.weapon_family).Build();

        of(SpellChangeStats.CAST_SPEED.get(), 1, 3, SlotTags.jewelry_family).Build();
        of(SpellChangeStats.COOLDOWN_REDUCTION.get(), 1, 3, SlotTags.jewelry_family).Build();

        for (Elements ele : Elements.getAllSingleElemental()) {
            if (ele != Elements.Physical) {
                of(new MaxElementalResist(ele), 1, 1, SlotTags.armor_family).Build();
                of(new ElementalResist(ele), 2, 5, SlotTags.armor_family).Build();
            }
        }

    }

    static AffixBuilder of(Stat stat, float v1, float v2, SlotTag fam) {

        return AffixBuilder.Normal(PREFIX + stat.GUID())
                .stat(stat.mod(v1, v2))
                .includesTags(fam)
                .GearCorrupt()
                .Weight(1000);
    }
}
