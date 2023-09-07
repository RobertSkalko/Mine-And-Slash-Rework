package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDamage;
import com.robertx22.age_of_exile.database.data.stats.types.gear_base.GearDefense;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class EnchantAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {
        String PREFIX = "enchantment_";

        AffixBuilder.Normal(PREFIX + "wep_dmg")
                .stat(GearDamage.getInstance().mod(2, 25).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.weapon_family)
                .Weight(1000)
                .Suffix()
                .Build();


        AffixBuilder.Normal(PREFIX + "gear_def")
                .stat(GearDefense.getInstance().mod(2, 25).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.armor_family)
                .Weight(1000)
                .Suffix()
                .Build();


        AffixBuilder.Normal(PREFIX + "off_def")
                .stat(GearDefense.getInstance().mod(2, 25).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.offhand_family)
                .Weight(1000)
                .Suffix()
                .Build();


        // jewelry
        AffixBuilder.Normal(PREFIX + "jewerly_int")
                .stat(DatapackStats.INT.mod(1, 10).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.jewelry_family)
                .Weight(1000)
                .Suffix()
                .Build();
        AffixBuilder.Normal(PREFIX + "jewerly_dex")
                .stat(DatapackStats.DEX.mod(1, 10).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.jewelry_family)
                .Weight(1000)
                .Suffix()
                .Build();
        AffixBuilder.Normal(PREFIX + "jewerly_str")
                .stat(DatapackStats.STR.mod(1, 10).percent())
                .includesTags(BaseGearType.SlotTag.enchantment, BaseGearType.SlotTag.jewelry_family)
                .Weight(1000)
                .Suffix()
                .Build();

    }
}
