package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.data.profession.stat.DoubleDropChance;
import com.robertx22.age_of_exile.database.data.profession.stat.ProfCategoryDropStat;
import com.robertx22.age_of_exile.database.data.profession.stat.ProfExp;
import com.robertx22.age_of_exile.database.data.profession.stat.TripleDropChance;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.tags.imp.SlotTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class ToolAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        HashMap<String, SlotTag> map = new HashMap<>();

        map.put(Professions.MINING, SlotTags.mining_tool);
        map.put(Professions.FARMING, SlotTags.farming_tool);
        map.put(Professions.HUSBANDRY, SlotTags.husbandry_tool);
        map.put(Professions.FISHING, SlotTags.fishing_tool);

        for (String prof : Professions.TOOL_PROFESSIONS) {

            AffixBuilder.Normal(prof + "_double")
                    .Named("")
                    .stats(new StatMod(1, 10, new DoubleDropChance(prof), ModType.FLAT))
                    .includesTags(SlotTags.tool, map.get(prof))
                    .Tool()
                    .Build();

            AffixBuilder.Normal(prof + "_triple")
                    .Named("")
                    .stats(new StatMod(1, 10, new TripleDropChance(prof), ModType.FLAT))
                    .includesTags(SlotTags.tool, map.get(prof))
                    .Tool()
                    .Build();

            AffixBuilder.Normal(prof + "_exp")
                    .Named("")
                    .stats(new StatMod(3, 15, new ProfExp(prof), ModType.FLAT))
                    .includesTags(SlotTags.tool, map.get(prof))
                    .Tool()
                    .Build();

            for (Profession.DropCategory cat : Profession.DropCategory.values()) {
                AffixBuilder.Normal(prof + "_" + cat.id + "_exp")
                        .Named("")
                        .stats(new StatMod(5, 20, new ProfCategoryDropStat(cat, prof), ModType.FLAT))
                        .includesTags(SlotTags.tool, map.get(prof))
                        .Tool()
                        .Build();
            }

        }
    }
}
