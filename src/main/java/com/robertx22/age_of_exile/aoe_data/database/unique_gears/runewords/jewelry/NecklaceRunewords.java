package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class NecklaceRunewords implements ExileRegistryInit {

    @Override
    public void registerAll() {
        UniqueGearBuilder.of(
                        "heavenly_tear",
                        "Heavenly Tear",
                        BaseGearTypes.NECKLACE)
                .setReplacesName()
                .stats(Arrays.asList(
                        new StatMod(10, 25, Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning)),
                        new StatMod(10, 20, Stats.HEAL_STRENGTH.get()),
                        new StatMod(5, 15, Armor.getInstance())
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.TOQ, RuneItem.RuneType.DOS, RuneItem.RuneType.YUN, RuneItem.RuneType.WIR))
                .devComment("divine school heal necklace")
                .build();

    }
}
