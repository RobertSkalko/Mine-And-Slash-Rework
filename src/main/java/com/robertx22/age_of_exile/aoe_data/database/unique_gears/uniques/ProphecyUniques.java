package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueRarityTier;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.league.LeagueMechanics;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class ProphecyUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("curse_of_tongues", "Curse of Tongues", BaseGearTypes.CLOTH_HELMET)
                .setReplacesName()

                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(50, 100).percent(),

                        DatapackStats.INT.mod(-20, -10).percent(),
                        DatapackStats.STR.mod(-20, -10).percent(),
                        DatapackStats.DEX.mod(-20, -10).percent(),

                        DatapackStats.HEALTH_PER_10_STR.mod(25, 50),
                        DatapackStats.MANA_PER_10_INT.mod(25, 50),
                        DatapackStats.ENERGY_PER_10_DEX.mod(25, 50)
                ))
                .leagueOnly((LeagueMechanics.PROPHECY).GUID())
                .build();

        UniqueGearBuilder.of("curse_of_numbers", "Curse of Numbers", BaseGearTypes.NECKLACE)
                .setReplacesName()

                .stats(Arrays.asList(
                        new StatMod(-25, -25, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Nature), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Cold), ModType.FLAT),

                        DatapackStats.INT.mod(10, 10).percent(),
                        DatapackStats.DEX.mod(10, 10).percent(),
                        DatapackStats.STR.mod(10, 10).percent(),

                        DatapackStats.MANA_PER_10_STR.mod(-50, 50),
                        DatapackStats.MANA_PER_10_INT.mod(-50, 50),
                        DatapackStats.MANA_PER_10_DEX.mod(-50, 50)
                ))
                .rarityWeight(UniqueRarityTier.UBER)
                .leagueOnly((LeagueMechanics.PROPHECY).GUID())
                .build();

        UniqueGearBuilder.of("the_beast", "The Beast", BaseGearTypes.PLATE_CHEST)
                .setReplacesName()

                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(50, 150).percent(),

                        new StatMod(-25, -25, Mana.getInstance(), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Shadow), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Fire), ModType.FLAT),

                        ResourceStats.LIFESTEAL.get().mod(1, 1),
                        ResourceStats.SPELL_LIFESTEAL.get().mod(1, 1),
                        DatapackStats.HEALTH_PER_10_STR.mod(25, 50),
                        HealthRegen.getInstance().mod(10, 25).percent()
                ))
                .leagueOnly((LeagueMechanics.PROPHECY).GUID())
                .build();


        UniqueGearBuilder.of("chained_oak", "Chained Oak", BaseGearTypes.LEATHER_CHEST)
                .setReplacesName()

                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(50, 100).percent(),

                        new StatMod(-25, -25, Mana.getInstance(), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Nature), ModType.FLAT),

                        OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.PHYSICAL).mod(15, 50),
                        DatapackStats.MANA_PER_10_DEX.mod(25, 75),
                        HealthRegen.getInstance().mod(10, 25).percent(),
                        DatapackStats.INT.mod(5, 15).percent()
                ))
                .leagueOnly((LeagueMechanics.PROPHECY).GUID())
                .build();


        UniqueGearBuilder.of("blood_curse", "Blood Curse", BaseGearTypes.RING)
                .setReplacesName()

                .stats(Arrays.asList(
                        new StatMod(-25, -25, Health.getInstance(), ModType.FLAT),
                        new StatMod(-50, -50, new ElementalResist(Elements.Shadow), ModType.FLAT),

                        OffenseStats.AREA_DAMAGE.get().mod(15, 50),
                        DatapackStats.MANA_PER_10_INT.mod(25, 50),
                        ManaRegen.getInstance().mod(10, 20).percent(),
                        DatapackStats.STR.mod(5, 15).percent()
                ))
                .leagueOnly((LeagueMechanics.PROPHECY).GUID())
                .build();

    }
}
