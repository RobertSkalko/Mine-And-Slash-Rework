package com.robertx22.mine_and_slash.aoe_data.database.affixes.adders;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.stat.DoubleDropChance;
import com.robertx22.mine_and_slash.database.data.profession.stat.ProfCategoryDropStat;
import com.robertx22.mine_and_slash.database.data.profession.stat.ProfExp;
import com.robertx22.mine_and_slash.database.data.profession.stat.TripleDropChance;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

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
                    .stats(new StatMod(2, 15, new DoubleDropChance(prof), ModType.FLAT))
                    .includesTags(SlotTags.tool, map.get(prof))
                    .Tool()
                    .Build();

            AffixBuilder.Normal(prof + "_triple")
                    .Named("")
                    .stats(new StatMod(2, 15, new TripleDropChance(prof), ModType.FLAT))
                    .includesTags(SlotTags.tool, map.get(prof))
                    .Tool()
                    .Build();

            AffixBuilder.Normal(prof + "_exp")
                    .Named("")
                    .stats(new StatMod(3, 20, new ProfExp(prof), ModType.FLAT))
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
