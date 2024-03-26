package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class JewelrySuffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_ele_dmg_jewelry")
                .add(Elements.Fire, "Of Embers")
                .add(Elements.Cold, "Of Ice")
                .add(Elements.Shadow, "Of Venom")
                .stats(x -> Arrays.asList(new StatMod(3, 10, Stats.ELEMENTAL_DAMAGE.get(x), ModType.FLAT)))
                .includesTags(SlotTags.jewelry_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_the_philosopher")
                .Named("Of the Philosopher")
                .coreStat(DatapackStats.INT)
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family)
                .excludesTags(SlotTags.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_the_titan")
                .Named("Of the Titan")
                .coreStat(DatapackStats.STR)
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family)
                .excludesTags(SlotTags.weapon_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_the_wind")
                .Named("Of the Wind")
                .coreStat(DatapackStats.DEX)
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family)
                .excludesTags(SlotTags.weapon_family)
                .Suffix()
                .Build();


        AffixBuilder.Normal("of_the_sky")
                .Named("Of the Sky")
                .stats(new StatMod(0.1F, 0.4F, AllAttributes.getInstance(), ModType.FLAT))
                .includesTags(SlotTags.jewelry_family)
                .Weight(50)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_the_troll")
                .Named("Of The Troll")
                .stats(new StatMod(3, 15, HealthRegen.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family, SlotTags.shield)
                .Weight(200)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_spirit_markings")
                .Named("Of Spirit Markings")
                .stats(new StatMod(3, 15, ManaRegen.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family, SlotTags.tome)
                .Weight(200)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_azure_skies")
                .Named("Of Azure Skies")
                .stats(new StatMod(3, 15, MagicShieldRegen.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.jewelry_family, SlotTags.tome)
                .Weight(200)
                .Suffix()
                .Build();


        AffixBuilder.Normal("of_treasure")
                .Named("Of Treasure")
                .stats(new StatMod(3, 10F, TreasureQuality.getInstance(), ModType.FLAT))
                .includesTags(SlotTags.jewelry_family)
                .Suffix()
                .Build();

        AffixBuilder.Normal("of_affluence")
                .Named("Of Affluence")
                .stats(new StatMod(3, 10, TreasureQuantity.getInstance(), ModType.FLAT))
                .includesTags(SlotTags.jewelry_family)
                .Suffix()
                .Build();

    }
}
