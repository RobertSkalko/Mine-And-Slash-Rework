package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueNecklaces implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                        "rabbit_paw",
                        "Rabbit's Paw",
                        BaseGearTypes.NECKLACE)
                .stats(Arrays.asList(
                        new StatMod(2, 10, DatapackStats.MOVE_SPEED, ModType.FLAT),
                        new StatMod(5, 25, EnergyRegen.getInstance(), ModType.PERCENT),
                        new StatMod(2, 6, DatapackStats.DEX, ModType.FLAT)
                ))
                .setReplacesName()
                .build();

        UniqueGearBuilder.of(
                        "ghast_necklace",
                        "Ghast Tear",
                        BaseGearTypes.NECKLACE)

                .stats(Arrays.asList(
                        new StatMod(25, 25, DatapackStats.MANA_PER_10_INT, ModType.FLAT),
                        new StatMod(15, 25, SkillDamage.getInstance(), ModType.FLAT),
                        new StatMod(1, 3, DatapackStats.STR, ModType.FLAT),
                        new StatMod(2, 6, DatapackStats.DEX, ModType.FLAT)
                ))

                .build();

        UniqueGearBuilder.of(
                        "skull_of_spirits",
                        "Skull of Spirits",
                        BaseGearTypes.NECKLACE)
                .stats(Arrays.asList(
                        new StatMod(1, 2, AllAttributes.getInstance(), ModType.FLAT),
                        new StatMod(10, 20, ManaRegen.getInstance(), ModType.PERCENT),
                        new StatMod(-5, -15, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(-5, -15, new ElementalResist(Elements.Fire), ModType.FLAT)
                ))
                .build();

    }
}
