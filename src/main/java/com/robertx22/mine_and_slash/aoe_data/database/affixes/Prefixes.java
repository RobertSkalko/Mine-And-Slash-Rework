package com.robertx22.mine_and_slash.aoe_data.database.affixes;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.*;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.corruption.CorruptJewelAffixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.corruption.CorruptionAffixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewelry.JewelryPrefixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewels.CraftedJewelAffixes;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.adders.jewels.JewelAffixes;
import com.robertx22.mine_and_slash.database.base.IRandomDefault;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.empty_entries.EmptyAffix;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileFilters;

import java.util.List;

public class Prefixes implements IRandomDefault<Affix>, ExileRegistryInit {

    @Override
    public void registerAll() {

        EmptyAffix.getInstance().registerToExileRegistry();

        CorruptJewelAffixes.init();
        CorruptionAffixes.init();

        new ToolAffixes().registerAll();

        new OffhandAffixes().registerAll();
        new JewelAffixes().registerAll();
        new CraftedJewelAffixes().registerAll();

        new WeaponPrefixes().registerAll();
        new ArmorPrefixes().registerAll();
        new JewelryPrefixes().registerAll();
        new ManaArmorAffixes().registerAll();

        new ImplicitAffixes().registerAll();

    }

    public static final Prefixes INSTANCE = new Prefixes();

    @Override
    public List<Affix> All() {
        return ExileDB.Affixes()
                .getWrapped()
                .of(ExileFilters.ofAffixType(Affix.Type.prefix))
                .list;
    }

}
