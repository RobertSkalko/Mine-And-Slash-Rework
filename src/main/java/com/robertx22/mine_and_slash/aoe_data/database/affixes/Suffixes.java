package com.robertx22.mine_and_slash.aoe_data.database.affixes;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.*;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewelry.JewelrySuffixes;
import com.robertx22.mine_and_slash.database.base.IRandomDefault;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileFilters;

import java.util.List;

public class Suffixes implements IRandomDefault<Affix>, ExileRegistryInit {

    public static Suffixes INSTANCE = new Suffixes();

    @Override
    public void registerAll() {
        new EnchantAffixes().registerAll();

        new GearSlotPowerAffixesAdder().registerAll();

        new WeaponSuffixes().registerAll();
        new NonWeaponSuffixes().registerAll();
        new JewelrySuffixes().registerAll();

        new ParagonGearAffixes().registerAll();

    }

    @Override
    public List<Affix> All() {
        return ExileDB.Affixes()
                .getWrapped()
                .of(ExileFilters.ofAffixType(Affix.AffixSlot.suffix)).list;
    }

}
