package com.robertx22.age_of_exile.aoe_data.database.runewords;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class Runewords implements ExileRegistryInit {

    @Override
    public void registerAll() {

        RunewordBuilder.of("abyssal_depths", "Abyssal Depths",
                Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        Health.getInstance().mod(5, 10).percent(),
                        Energy.getInstance().mod(10, 25).percent(),
                        Mana.getInstance().mod(10, 25).percent(),
                        Stats.SUMMON_DAMAGE.get().mod(10, 25)
                ),
                Arrays.asList(RuneType.NOS, RuneType.MOS, RuneType.ITA),
                GearSlots.CHEST);

    }
}
