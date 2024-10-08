package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

public class JewelryPrefixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("archeologists")
                .Named("Archeologist's")
                .stats(new StatMod(3, 8, TreasureQuality.getInstance(), ModType.FLAT))
                .includesTags(SlotTags.jewelry_family)
                .Prefix()
                .Build();

        AffixBuilder.Normal("vampiric")
                .Named("Vampiric")
                .Weight(100)
                .stats(new StatMod(1, 2, ResourceStats.LIFESTEAL.get(), ModType.FLAT))
                .includesTags(SlotTags.jewelry_family)
                .Prefix()
                .Build();

        AffixBuilder.Normal("scavanger")
                .Named("Scavanger")
                .stats(new StatMod(2, 8, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.health)))
                .includesTags(SlotTags.jewelry_family)
                
                .Prefix()
                .Build();

        AffixBuilder.Normal("mana_thief")
                .Named("Mana Thief's")
                .stats(new StatMod(2, 8, ResourceStats.RESOURCE_ON_KILL.get(ResourceType.mana)))
                .includesTags(SlotTags.jewelry_family)
                .Prefix()
                .Build();

    }
}
