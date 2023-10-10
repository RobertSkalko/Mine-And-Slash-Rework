package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType.SlotTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class WeaponOrNecklaceSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {
        AffixBuilder.Normal("precise")
                .Named("Precise")
                .stats(new StatMod(4, 20, Stats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTag.necklace, SlotTag.weapon_family)
                .Prefix()
                .Build();
        AffixBuilder.Normal("of_brutality")
                .Named("Of Brutality")
                .stats(new StatMod(6, 50, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTag.necklace, SlotTag.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("focused")
                .Named("Focused")
                .stats(new StatMod(4, 20, Stats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTag.necklace, SlotTag.mage_weapon)
                .Prefix()
                .Build();
        AffixBuilder.Normal("of_devastation")
                .Named("Of Devastation")
                .stats(new StatMod(6, 50, Stats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTag.necklace, SlotTag.mage_weapon)
                .Suffix()
                .Build();
    }
}
