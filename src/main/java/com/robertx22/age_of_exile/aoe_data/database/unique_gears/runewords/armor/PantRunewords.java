package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.armor;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class PantRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of("weight_of_leadership", "Weight of Leadership", BaseGearTypes.CLOTH_PANTS)
                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(50, 100).percent(),
                        Energy.getInstance().mod(15, 30).percent(),
                        EnergyRegen.getInstance().mod(15, 30).percent(),
                        Stats.SUMMON_DAMAGE.get().mod(30, 30),
                        Armor.getInstance().mod(-25, -25).more(),
                        DodgeRating.getInstance().mod(-25, -25).more()
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.ENO, RuneItem.RuneType.HAR, RuneItem.RuneType.FEY, RuneItem.RuneType.NOS, RuneItem.RuneType.XER))
                .build();

    }
}
