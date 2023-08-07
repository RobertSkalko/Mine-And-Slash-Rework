package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.gear.OrbAffixUpgrade;
import com.robertx22.age_of_exile.database.data.currency.skill_gem.OrbOfLinking;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class Currencies implements ExileRegistryInit {


    public static List<Currency> ALL = new ArrayList<>();

    static {
        ALL.add(new OrbOfLinking());
        ALL.add(new OrbAffixUpgrade());
    }

    @Override
    public void registerAll() {


        for (Currency c : ALL) {
            c.registerToExileRegistry();
        }

    }

}
