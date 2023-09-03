package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.database.data.profession.MaterialItem;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;

import java.util.HashMap;

public class MaterialItems {

    public static HashMap<SkillItemTier, RegObj<Item>> MINING_ORE = new HashMap<>();

    public static void init() {

        for (SkillItemTier tier : SkillItemTier.values()) {

            MINING_ORE.put(tier, Def.item("material/mining_ore/" + tier.tier, () -> new MaterialItem(tier, "Ore")));
        }

    }
}
