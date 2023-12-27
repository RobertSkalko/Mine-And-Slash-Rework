package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.weapon;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class BowUniques implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("bone_shatterer", "Bone Shatterer", BaseGearTypes.BOW)
                .stats(Arrays.asList(
                        new StatMod(25, 50, Stats.CRIT_DAMAGE.get()),
                        new StatMod(20, 40, Stats.DAMAGE_TO_UNDEAD.get()),
                        new StatMod(5, 5, Stats.LIFESTEAL.get())
                ))
                .devComment("crit dmg to undead bow")
                .build();
        
        UniqueGearBuilder.of("critical_impossibility", "Critical Impossibility", BaseGearTypes.BOW)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(50, 150, GearDamage.getInstance(), ModType.PERCENT),
                        new StatMod(-50, -50, Stats.CRIT_CHANCE.get(), ModType.FLAT),
                        new StatMod(50, 200, Stats.CRIT_DAMAGE.get(), ModType.FLAT)))
                .build();
    }
}
