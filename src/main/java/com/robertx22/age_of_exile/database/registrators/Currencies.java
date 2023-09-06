package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.database.data.currency.base.Currency;
import com.robertx22.age_of_exile.database.data.currency.gear.*;
import com.robertx22.age_of_exile.database.data.currency.skill_gem.OrbOfLinking;
import com.robertx22.age_of_exile.mechanics.harvest.currency.EntangledAffixUpgrade;
import com.robertx22.age_of_exile.mechanics.harvest.currency.EntangledQuality;
import com.robertx22.age_of_exile.mechanics.harvest.currency.EntangledUniqueReroll;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.temp.SkillItemTier;

import java.util.ArrayList;
import java.util.List;

public class Currencies implements ExileRegistryInit {


    public static List<Currency> ALL = new ArrayList<>();

    static {
        ALL.add(new OrbOfLinking());
        ALL.add(new OrbAffixUpgrade());
        ALL.add(new OrbSocketAdder());
        ALL.add(new QualityUpgrade());
        ALL.add(new EntangledAffixUpgrade());
        ALL.add(new EntangledQuality());
        ALL.add(new OrbUniqueReroll());
        ALL.add(new EntangledUniqueReroll());

        for (SkillItemTier tier : SkillItemTier.values()) {
            ALL.add(new SharpeningStone(tier));
        }


    }

    @Override
    public void registerAll() {


        for (Currency c : ALL) {
            c.registerToExileRegistry();
        }

    }

}
