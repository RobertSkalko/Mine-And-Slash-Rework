package com.robertx22.age_of_exile.database.data.chaos_stats;

import com.robertx22.age_of_exile.database.data.affixes.Affix;

import java.util.ArrayList;
import java.util.List;

// todo will be used for both uniques, runed items, normal items etc

public class ChaosStat {

    public int affix_number = 0;
    public Affix.Type affix_type = Affix.Type.chaos_stat;
    public int bonus_sockets = 0;

    public List<String> for_item_rarities = new ArrayList<>();

}
