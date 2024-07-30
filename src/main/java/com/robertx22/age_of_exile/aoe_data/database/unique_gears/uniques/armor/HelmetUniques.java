package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armor;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueRarityTier;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class HelmetUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {
        UniqueGearBuilder.of(
                        "insight",
                        "Insight",
                        BaseGearTypes.CLOTH_HELMET)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(20, 30, ManaRegen.getInstance(), ModType.PERCENT),
                        new StatMod(10, 25, Mana.getInstance(), ModType.FLAT),
                        new StatMod(10, 25, new ElementalResist(Elements.Fire), ModType.FLAT)
                ))
                .devComment("Mana focused caster helmet")
                .build();

        UniqueGearBuilder.of("frostburn_prot", "Frostburn Ward", BaseGearTypes.CLOTH_HELMET)
                .stats(Arrays.asList(
                        new StatMod(25, 50, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(25, 50, new AilmentResistance(Ailments.FREEZE), ModType.FLAT),
                        new StatMod(25, 50, new AilmentResistance(Ailments.BURN), ModType.FLAT),
                        new StatMod(15, 25, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(15, 25, new ElementalResist(Elements.Cold), ModType.FLAT)
                ))
                .build();

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
                        new StatMod(10, 25, AuraCapacity.getInstance(), ModType.FLAT),
                        new StatMod(10, 10, AuraEffect.getInstance(), ModType.FLAT),
                        new StatMod(5, 15, OffenseStats.DAMAGE_PER_SPELL_TAG.get(SpellTags.beast), ModType.FLAT),
                        new StatMod(1, 1, SpellChangeStats.MAX_SUMMON_CAPACITY.get(), ModType.FLAT)
                ))
                .build();


        UniqueGearBuilder.of("haste_price", "Price of Haste", BaseGearTypes.PLATE_HELMET)
                .keepsBaseName()
                .stat(GearDefense.getInstance().mod(50, 100).percent())
                .stat(SpellChangeStats.COOLDOWN_REDUCTION.get().mod(15, 30))
                .stat(SpellChangeStats.MANA_COST.get().mod(15, 30))
                .stat(new ElementalResist(Elements.Physical).mod(5, 10))
                .build();
    }
}
