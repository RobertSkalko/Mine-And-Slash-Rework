package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapon;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class StaffUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("doombolt", "Touch of Doom", BaseGearTypes.BOW)
                .stats(Arrays.asList(
                        new StatMod(0, 100, GearDamage.getInstance()).percent(),
                        new StatMod(15, 30, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow)),
                        new StatMod(1, 1, ResourceStats.SPELL_LIFESTEAL.get()),
                        new StatMod(10, 25, OffenseStats.DOT_DAMAGE.get())
                ))
                .build();

        UniqueGearBuilder.of("arch_gamble", "The Arch-Gamble", BaseGearTypes.STAFF)
                .stats(Arrays.asList(
                        new StatMod(-150, 150, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(-150, 150, OffenseStats.DOT_DAMAGE.get()),
                        new StatMod(5, 5, ResourceStats.SPELL_LIFESTEAL.get())
                ))
                .build();

        UniqueGearBuilder.of("crystalized_capacitor", "Crystalized Capacitor", BaseGearTypes.STAFF)
                .stats(Arrays.asList(
                        new StatMod(25, 50, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(25, 150, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature)),
                        new StatMod(-150, -150, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold)),
                        new StatMod(10, 25, OffenseStats.CRIT_CHANCE.get())
                ))
                .build();

        UniqueGearBuilder.of("divine_frost", "Divine Frost", BaseGearTypes.STAFF)
                .stats(Arrays.asList(
                        new StatMod(25, 100, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(25, 75, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold)),
                        new StatMod(25, 25, ResourceStats.HEAL_STRENGTH.get()),
                        new StatMod(10, 25, SpellChangeStats.INCREASED_AREA.get())
                ))
                .build();

        UniqueGearBuilder.of("new_dawn_flame", "Flame of new Dawn", BaseGearTypes.STAFF)
                .stats(Arrays.asList(
                        new StatMod(50, 50, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(50, 100, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire)),
                        new StatMod(10, 25, new AilmentChance(Ailments.BURN)),
                        new StatMod(10, 25, OffenseStats.AREA_DAMAGE.get())
                ))
                .build();


    }
}
