package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.offhand;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueRarityTier;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class ShieldUniques implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of("ele_aegis", "Elemental Aegis", BaseGearTypes.ARMOR_SHIELD)
                .keepsBaseName()
                .rarityWeight(UniqueRarityTier.UBER)
                .stat(GearDefense.getInstance().mod(50, 150).percent())
                .stat(AuraEffect.getInstance().mod(5, 5))
                .stat(new MaxElementalResist(Elements.Fire).mod(1, 3))
                .stat(new MaxElementalResist(Elements.Cold).mod(1, 3))
                .stat(new MaxElementalResist(Elements.Chaos).mod(1, 3))
                .build();

    }
}
