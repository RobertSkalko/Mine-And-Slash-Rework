package com.robertx22.age_of_exile.aoe_data.database.unique_gears.fabled;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class FabledJewelry implements ExileRegistryInit {

    @Override
    public void registerAll() {
        UniqueGearBuilder.of(
                        "azuna_ring",
                        "Azuna's Eternal Decree",
                        BaseGearTypes.RING)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(-50, 50, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-50, 50, new ElementalResist(Elements.Chaos), ModType.FLAT),
                        new StatMod(-50, 15, TreasureQuality.getInstance(), ModType.FLAT),
                        new StatMod(-50, 15, TreasureQuantity.getInstance(), ModType.FLAT)
                ))

                .devComment("God's ring: item find and luck")
                .build();

    }
}
