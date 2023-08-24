package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class OffhandAffixes implements ExileRegistryInit {
    @Override
    public void registerAll() {

        AffixBuilder.Normal("promised")
                .Named("Promised")
                .stats(new StatMod(0.5F, 3, MagicShieldRegen.getInstance(), ModType.FLAT))
                .includesTags(BaseGearType.SlotTag.tome)
                .Weight(500)
                .Prefix()
                .Build();

        AffixBuilder.Normal("boundless")
                .Named("Boundless")
                .stats(new StatMod(0.5F, 3, ManaRegen.getInstance(), ModType.FLAT))
                .includesTags(BaseGearType.SlotTag.tome)
                .Weight(500)
                .Prefix()
                .Build();

        AffixBuilder.Normal("energetic")
                .Named("Energetic")
                .stats(new StatMod(0.5F, 3, EnergyRegen.getInstance(), ModType.FLAT))
                .includesTags(BaseGearType.SlotTag.flute)
                .Weight(500)
                .Prefix()
                .Build();

        AffixBuilder.Normal("immortal")
                .Named("Immortal")
                .stats(new StatMod(0.5F, 3, HealthRegen.getInstance(), ModType.FLAT))
                .includesTags(BaseGearType.SlotTag.shield)
                .Weight(500)
                .Prefix()
                .Build();

    }
}
