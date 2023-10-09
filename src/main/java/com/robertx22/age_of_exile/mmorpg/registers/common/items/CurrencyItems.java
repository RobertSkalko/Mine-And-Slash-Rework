package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.currency.CurrencyItem;
import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.registrators.Currencies;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;

import java.util.HashMap;
import java.util.function.Supplier;

public class CurrencyItems {

    public static void init() {

        Currencies.init();
        
        for (Currency cur : Currencies.ALL) {
            currency(() -> new CurrencyItem(cur), cur.GUID());
        }
    }


    public static HashMap<String, RegObj<CurrencyItem>> map = new HashMap<>();


    public static RegObj<CurrencyItem> currency(Supplier<CurrencyItem> object, String id) {
        var b = Def.item("currency/" + id, object);
        map.put(id, b);
        return b;
    }


}
