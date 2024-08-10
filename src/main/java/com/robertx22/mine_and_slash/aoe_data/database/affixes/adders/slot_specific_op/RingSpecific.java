package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.slot_specific_op;

import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.Mana;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class RingSpecific implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("of_energy")
                .Named("Of Energy")
                .stats(new StatMod(6, 15, Energy.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.ring, SlotTags.jewel_dex)
                .Suffix()
                .Build();
        AffixBuilder.Normal("of_mana")
                .Named("Of Mana")
                .stats(new StatMod(6, 15, Mana.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.ring, SlotTags.jewel_int)
                .Suffix()
                .Build();

        AffixBuilder.Normal("mana_reg")
                .Named("Soothing")
                .stats(new StatMod(6, 15, ManaRegen.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.ring, SlotTags.jewel_int)
                .Prefix()
                .Build();


        AffixBuilder.Normal("energy_reg")
                .Named("Invirogating")
                .stats(new StatMod(6, 15, EnergyRegen.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.ring, SlotTags.jewel_dex)
                .Prefix()
                .Build();

        AffixBuilder.Normal("ms_reg")
                .Named("Stabilizing")
                .stats(new StatMod(6, 15, MagicShield.getInstance(), ModType.PERCENT))
                .includesTags(SlotTags.ring, SlotTags.jewel_int)
                .Prefix()
                .Build();


    }
}
