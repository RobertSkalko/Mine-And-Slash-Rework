package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SalvagedDustItem;
import com.robertx22.temp.SkillItemTier;

import java.util.HashMap;

public class ProfessionItems {

    public static HashMap<SkillItemTier, RegObj<SalvagedDustItem>> SALVAGED_ESSENCE_MAP = new HashMap<>();

    public static void init() {

        for (SkillItemTier tier : SkillItemTier.values()) {

            SALVAGED_ESSENCE_MAP.put(tier, Def.item(() -> new SalvagedDustItem("Tier " + tier.tier + " Purified Essence", tier, tier.levelRange, tier.repairDurab)));

      
        }

    }
}
