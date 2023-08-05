package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.currency.OrbOfTransmutationItem;
import com.robertx22.age_of_exile.database.data.currency.base.CurrencyItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyItems {

    public static void init() {

    }

    public static List<CurrencyItem> getAllCurrenciesFromRegistry() {
        return ForgeRegistries.ITEMS.getValues()
                .stream()
                .filter(x -> x instanceof CurrencyItem)
                .map(x -> (CurrencyItem) x)
                .collect(Collectors.toList());
    }

    public static RegObj<CurrencyItem> ORB_OF_TRANSMUTATION = Def.item(() -> new OrbOfTransmutationItem(), "orb_of_transmutation");


/*
     public static RegObj<CurrencyItem> ORB_OF_DISORDER = Def.item(() -> new OrbOfDisorder());
    public static RegObj<CurrencyItem> ORB_OF_TURBULENCE = Def.item(() -> new OrbOfTurbulence());
    public static RegObj<CurrencyItem> LEAF_OF_CHANGE = Def.item(() -> new LeafOfChangeItem());
    public static RegObj<CurrencyItem> ORB_OF_BLESSING = Def.item(() -> new OrbOfBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_UNIQUE_BLESSING = Def.item(() -> new OrbOfUniqueBlessingItem());
    public static RegObj<CurrencyItem> ORB_OF_CORRUPTION = Def.item(() -> new OrbOfCorruption());


 */

}
