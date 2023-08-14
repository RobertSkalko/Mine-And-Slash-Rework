package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armor;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueRarityTier;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCostReduction;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class HelmetUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("mana_dominion", "Dominion of Mana", BaseGearTypes.CLOTH_HELMET)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(25, 50, Mana.getInstance(), ModType.PERCENT),
                        new StatMod(-25, -25, Energy.getInstance(), ModType.PERCENT),
                        new StatMod(-25, 25, Health.getInstance(), ModType.PERCENT),
                        new StatMod(1, 10, DatapackStats.MANA_PER_10_INT, ModType.FLAT)
                ))
                .devComment("mana crit helmet")
                .build();

        UniqueGearBuilder.of("pack_leader", "Leader of the Pack", BaseGearTypes.LEATHER_HELMET)
                .setReplacesName()
                .rarityWeight(UniqueRarityTier.RARE)
                .stats(Arrays.asList(
                        new StatMod(10, 25, AuraCostReduction.getInstance(), ModType.FLAT),
                        new StatMod(10, 10, AuraEffect.getInstance(), ModType.FLAT),
                        new StatMod(5, 15, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.beast), ModType.FLAT),
                        new StatMod(1, 1, Stats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT)
                ))
                .build();
    }
}
