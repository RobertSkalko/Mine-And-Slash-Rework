package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.weapon;

import com.robertx22.age_of_exile.aoe_data.database.GearDataHelper;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class StaffRuneWords implements ExileRegistryInit, GearDataHelper {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of("doombolt", "Touch of Doom", BaseGearTypes.BOW)
                .stats(Arrays.asList(
                        new StatMod(0, 100, GearDamage.getInstance()).percent(),
                        new StatMod(15, 30, Stats.ELEMENTAL_DAMAGE.get(Elements.Chaos)),
                        new StatMod(1, 1, Stats.SPELL_LIFESTEAL.get()),
                        new StatMod(10, 25, Stats.DOT_DAMAGE.get())
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.XER, RuneItem.RuneType.DOS, RuneItem.RuneType.TOQ, RuneItem.RuneType.ORU))
                .build();
    }
}
