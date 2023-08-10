package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;

import java.util.HashMap;

public class RarityItems {

    public static HashMap<Integer, RegObj<RarityStoneItem>> RARITY_STONE = new HashMap<>();

    public static void init() {


        for (int rar = 0; rar < IRarity.TOTAL_GEAR_RARITIES; rar++) {

            int finalRar = rar;
            RARITY_STONE.put(rar, Def.item(() -> new RarityStoneItem("Stone", finalRar), "stone/" + finalRar));
        }

    }
}
