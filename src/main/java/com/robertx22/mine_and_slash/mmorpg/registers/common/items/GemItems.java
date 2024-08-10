package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.GemItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GemItems {

    public static HashMap<GemItem.GemType, HashMap<GemItem.GemRank, RegObj<GemItem>>> MAP = new HashMap<>();
    public static List<RegObj<GemItem>> ALL = new ArrayList<>();

    public static void init() {

        for (GemItem.GemType type : GemItem.GemType.values()) {
            for (GemItem.GemRank rank : GemItem.GemRank.values()) {

               
                RegObj<GemItem> def = Def.item(() -> new GemItem(type, rank), "gems/" + type.id + "/" + rank.tier);

                if (!MAP.containsKey(type)) {
                    MAP.put(type, new HashMap<>());
                }

                MAP.get(type)
                        .put(rank, def);

                ALL.add(def);

            }
        }

    }

}
