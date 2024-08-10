package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ManaArmorAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        AffixBuilder.Normal("glimmering")
                .Named("Glimmering")
                .stats(new StatMod(4, 15, Mana.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.armor_family, SlotTags.jewel_int)
                .Prefix()
                .Build();

        AffixBuilder.Normal("seraphim")
                .Named("Seraphim's")
                .stats(new StatMod(4, 10, Mana.getInstance(), ModType.PERCENT),
                        new StatMod(3, 6, Health.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.armor_family, SlotTags.jewel_int)
                .Prefix()
                .Build();

        AffixBuilder.Normal("azure")
                .Named("Azure")
                .stats(new StatMod(2, 14, Mana.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.armor_family, SlotTags.jewel_int)
                .Prefix()
                .Build();

    }
}

