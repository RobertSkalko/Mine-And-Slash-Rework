package com.robertx22.mine_and_slash.database.registry;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;

import java.util.function.Predicate;

public class ExileFilters {

    public static Predicate<Affix> ofAffixType(Affix.AffixSlot type) {
        return x -> x.type == type;
    }

}
