package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class WeaponOrNecklaceSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {
        AffixBuilder.Normal("precise")
                .Named("Precise")
                .stats(new StatMod(4, 20, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTags.necklace, SlotTags.weapon_family)
                .Prefix()
                .Build();
        AffixBuilder.Normal("of_brutality")
                .Named("Of Brutality")
                .stats(new StatMod(6, 50, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTags.necklace, SlotTags.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("focused")
                .Named("Focused")
                .stats(new StatMod(4, 20, OffenseStats.CRIT_CHANCE.get(), ModType.FLAT))
                .includesTags(SlotTags.necklace, SlotTags.mage_weapon)
                .Prefix()
                .Build();
        AffixBuilder.Normal("of_devastation")
                .Named("Of Devastation")
                .stats(new StatMod(6, 50, OffenseStats.CRIT_DAMAGE.get(), ModType.FLAT))
                .includesTags(SlotTags.necklace, SlotTags.mage_weapon)
                .Suffix()
                .Build();
    }
}
