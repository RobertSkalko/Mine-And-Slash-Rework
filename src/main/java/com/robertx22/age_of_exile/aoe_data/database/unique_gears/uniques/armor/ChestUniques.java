package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armor;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueRarityTier;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.summon.GolemSpellChance;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class ChestUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {


        UniqueGearBuilder.of("bulwark", "Bulwark", BaseGearTypes.PLATE_CHEST)
                .keepsBaseName()
                .stats(Arrays.asList(
                        new StatMod(100, 250, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(100, 100, new AilmentResistance(Ailments.BLEED), ModType.FLAT),
                        new StatMod(-25, -25, Energy.getInstance(), ModType.PERCENT),
                        new StatMod(-25, -25, EnergyRegen.getInstance(), ModType.PERCENT)
                ))
                .build();


        UniqueGearBuilder.of("golemancer", "Golemancer", BaseGearTypes.PLATE_CHEST)
                .keepsBaseName()
                .stats(Arrays.asList(
                        new StatMod(50, 100, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(5, 15, GolemSpellChance.getInstance()),
                        new StatMod(10, 25, Stats.SUMMON_DAMAGE.get(), ModType.FLAT),
                        new StatMod(25, 25, new ElementalResist(Elements.Fire), ModType.FLAT)
                ))
                .build();

        UniqueGearBuilder.of("towering_physique", "Towering Physique", BaseGearTypes.CLOTH_CHEST)
                .keepsBaseName()
                .rarityWeight(UniqueRarityTier.RARE)
                .stat(GearDefense.getInstance().mod(50, 100).percent())
                .stat(RegeneratePercentStat.MANA.mod(1, 2))
                .stat(Stats.ATTACK_STYLE_DAMAGE.get(AttackStyle.SPELL).mod(20, 20))
                .stat(new ElementalResist(Elements.Physical).mod(10, 25))
                .build();
    }
}
