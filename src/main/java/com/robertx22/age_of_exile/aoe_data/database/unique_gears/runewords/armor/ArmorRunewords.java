package com.robertx22.age_of_exile.aoe_data.database.unique_gears.runewords.armor;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentResistance;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class ArmorRunewords implements ExileRegistryInit {
    @Override
    public void registerAll() {

        UniqueGearBuilder.of("frostburn_prot", "Frostburn Ward", BaseGearTypes.CLOTH_HELMET)
                .stats(Arrays.asList(
                        new StatMod(25, 50, GearDefense.getInstance(), ModType.FLAT),
                        new StatMod(25, 50, new AilmentResistance(Ailments.FREEZE), ModType.FLAT),
                        new StatMod(25, 50, new AilmentResistance(Ailments.BURN), ModType.FLAT),
                        new StatMod(15, 25, new ElementalResist(Elements.Fire), ModType.FLAT),
                        new StatMod(15, 25, new ElementalResist(Elements.Cold), ModType.FLAT)
                ))
                .makeRuneWordOnly(Arrays.asList(RuneItem.RuneType.ITA, RuneItem.RuneType.DOS, RuneItem.RuneType.NOS))
                .build();
        
    }

}
