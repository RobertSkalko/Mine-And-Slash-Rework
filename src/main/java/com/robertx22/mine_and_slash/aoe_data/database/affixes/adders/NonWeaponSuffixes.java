package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class NonWeaponSuffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_res")
                .add(Elements.Fire, "Of the Drake")
                .add(Elements.Nature, "Of the Yeti")
                .add(Elements.Cold, "Of the Storm")
                .stats(x -> Arrays.asList(new StatMod(10, 45, new ElementalResist(x), ModType.FLAT)))
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family, SlotTags.offhand_family)
                .Weight(5000)
                .Suffix()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_res")
                .add(Elements.Shadow, "Of the Snake")
                .stats(x -> Arrays.asList(new StatMod(10, 32, new ElementalResist(x), ModType.FLAT)))
                .includesTags(SlotTags.jewelry_family, SlotTags.armor_family, SlotTags.offhand_family)
                .Weight(3000)
                .Suffix()
                .Build();

    }

}


