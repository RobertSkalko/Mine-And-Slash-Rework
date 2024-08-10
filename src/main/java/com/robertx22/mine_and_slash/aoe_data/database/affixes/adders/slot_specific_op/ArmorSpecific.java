package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ArmorSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("strong_int_armor_suf")
                .Named("Of the Genius")
                .bigCoreStat(DatapackStats.INT)
                .includesTags(SlotTags.armor_family)
                .Suffix()
                .Weight(500)
                .Build();

        AffixBuilder.Normal("strong_str_armor_suf")
                .Named("Of the Monster")
                .bigCoreStat(DatapackStats.STR)
                .includesTags(SlotTags.armor_family)
                .Suffix()
                .Weight(500)
                .Build();

        AffixBuilder.Normal("strong_dex_armor_suf")
                .Named("Of Skill")
                .bigCoreStat(DatapackStats.DEX)
                .includesTags(SlotTags.armor_family)
                .Suffix()
                .Weight(500)
                .Build();


    }
}
