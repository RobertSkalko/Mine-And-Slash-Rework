package com.robertx22.age_of_exile.aoe_data.database.affixes.adders.jewels;

import com.robertx22.age_of_exile.aoe_data.database.affixes.ElementalAffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class JewelAffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        // jewels dont need names
        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_jewel_res")
                .add(Elements.Fire, "")
                .add(Elements.Lightning, "")
                .add(Elements.Cold, "")
                .stats(x -> Arrays.asList(new StatMod(10, 25, new ElementalResist(x), ModType.FLAT)))
                .includesTags(BaseGearType.SlotTag.any_jewel)
                .Weight(5000)
                .Suffix()
                .Build();

        ElementalAffixBuilder.start()
                .guid(x -> x.guidName + "_jewel_res")
                .add(Elements.Chaos, "")
                .stats(x -> Arrays.asList(new StatMod(10, 20, new ElementalResist(x), ModType.FLAT)))
                .includesTags(BaseGearType.SlotTag.any_jewel)
                .Weight(3000)
                .Suffix()
                .Build();
    }
}
