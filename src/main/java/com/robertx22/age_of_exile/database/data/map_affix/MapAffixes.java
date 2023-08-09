package com.robertx22.age_of_exile.database.data.map_affix;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.List;

public class MapAffixes {

    public static void init() {

        List<Elements> elements = Elements.getAllSingleElementals();

        for (Elements element : elements) {

            new MapAffix(element.guidName + "_atk").addMod(new StatMod(2, 6, new BonusAttackDamage(element))).affectsPlayer().registerToExileRegistry();

        }

    }
}
