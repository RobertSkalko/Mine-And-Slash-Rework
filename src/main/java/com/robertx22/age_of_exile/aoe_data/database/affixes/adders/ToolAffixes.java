package com.robertx22.age_of_exile.aoe_data.database.affixes.adders;

import com.robertx22.age_of_exile.aoe_data.database.affixes.AffixBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.data.profession.stat.DoubleDropChance;
import com.robertx22.age_of_exile.database.data.profession.stat.ProfExp;
import com.robertx22.age_of_exile.database.data.profession.stat.TripleDropChance;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.HashMap;

public class ToolAffixes implements ExileRegistryInit {

    @Override
    public void registerAll() {

        HashMap<String, BaseGearType.SlotTag> map = new HashMap<>();

        map.put(Professions.MINING, BaseGearType.SlotTag.mining_tool);
        map.put(Professions.FARMING, BaseGearType.SlotTag.farming_tool);
        map.put(Professions.HUSBANDRY, BaseGearType.SlotTag.husbandry_tool);
        map.put(Professions.FISHING, BaseGearType.SlotTag.fishing_tool);

        for (String prof : Professions.TOOL_PROFESSIONS) {

            AffixBuilder.Normal(prof + "_double")
                    .Named("")
                    .stats(new StatMod(1, 10, new DoubleDropChance(prof), ModType.FLAT))
                    .includesTags(BaseGearType.SlotTag.tool, map.get(prof))
                    .Prefix()
                    .Build();

            AffixBuilder.Normal(prof + "_triple")
                    .Named("")
                    .stats(new StatMod(1, 10, new TripleDropChance(prof), ModType.FLAT))
                    .includesTags(BaseGearType.SlotTag.tool, map.get(prof))
                    .Prefix()
                    .Build();

            AffixBuilder.Normal(prof + "_exp")
                    .Named("")
                    .stats(new StatMod(3, 15, new ProfExp(prof), ModType.FLAT))
                    .includesTags(BaseGearType.SlotTag.tool, map.get(prof))
                    .Prefix()
                    .Build();
        }
    }
}
