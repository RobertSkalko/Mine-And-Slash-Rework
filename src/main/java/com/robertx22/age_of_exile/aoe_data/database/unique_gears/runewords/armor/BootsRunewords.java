package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.armor;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentProcStat;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class BootsRunewords implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("ice_stomper", "Ice Stomper", BaseGearTypes.PLATE_BOOTS)
                .stats(Arrays.asList(
                        new StatMod(25, 150, GearDefense.getInstance(), ModType.PERCENT),
                        new StatMod(25, 50, new AilmentProcStat(Ailments.FREEZE), ModType.FLAT),
                        new StatMod(25, 50, new AilmentProcStat(Ailments.ELECTRIFY), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Chaos), ModType.FLAT)
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.YUN, RuneItem.RuneType.ANO, RuneItem.RuneType.DOS, RuneItem.RuneType.NOS))
                .build();

        UniqueGearBuilder.of("sparkfinder", "Sparkfinder", BaseGearTypes.LEATHER_BOOTS)
                .stats(Arrays.asList(
                        GearDefense.getInstance().mod(25, 100).percent(),
                        TreasureQuality.getInstance().mod(5, 25).percent(),
                        new ElementalResist(Elements.Fire).mod(-100, 100).percent()
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.YUN, RuneItem.RuneType.FEY, RuneItem.RuneType.DOS, RuneItem.RuneType.XER))
                .build();
    }
}
