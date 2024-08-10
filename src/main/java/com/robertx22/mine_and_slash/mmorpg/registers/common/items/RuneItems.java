package com.robertx22.mine_and_slash.mmorpg.registers.common.items;

import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.RuneItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.RuneType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuneItems {

    public static HashMap<String, RegObj<RuneItem>> MAP = new HashMap<>();
    public static List<RegObj<RuneItem>> ALL = new ArrayList<>();

    public static void init() {

        for (RuneType type : RuneType.values()) {

            RegObj<RuneItem> def = Def.item(() -> new RuneItem(type), "runes/" + type.id);

            MAP.put(type.id, def);
            ALL.add(def);

        }

    }

}
