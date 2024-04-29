package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.armor;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class PantsUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("weight_of_leadership", "Weight of Leadership", BaseGearTypes.CLOTH_PANTS)
                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(50, 100).percent(),
                        Energy.getInstance().mod(15, 30).percent(),
                        EnergyRegen.getInstance().mod(15, 30).percent(),
                        OffenseStats.SUMMON_DAMAGE.get().mod(30, 30),
                        Armor.getInstance().mod(-25, -25).more(),
                        DodgeRating.getInstance().mod(-25, -25).more()
                ))
                .build();

        UniqueGearBuilder.of("burning_desire", "Burning Desire", BaseGearTypes.PLATE_CHEST)
                .keepsBaseName()
                .stats(Arrays.asList(
                        new StatMod(50, 100, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(10, 25, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                        new StatMod(10, 25, new AilmentChance(Ailments.BURN), ModType.FLAT),
                        new StatMod(-25, -25, SpellChangeStats.COOLDOWN_REDUCTION.get(), ModType.PERCENT)
                ))
                .build();

        UniqueGearBuilder.of("sculpted_perfection", "Sculpted Perfection", BaseGearTypes.CLOTH_PANTS)
                .keepsBaseName()
                .stat(GearDefense.getInstance().mod(50, 150).percent())
                .stat(AuraEffect.getInstance().mod(5, 15))
                .stat(new ElementalResist(Elements.Fire).mod(10, 10))
                .stat(new ElementalResist(Elements.Cold).mod(10, 10))
                .stat(new ElementalResist(Elements.Shadow).mod(10, 10))
                .build();

    }
}
