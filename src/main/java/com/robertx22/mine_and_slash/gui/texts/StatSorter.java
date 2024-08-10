package com.robertx22.mine_and_slash.gui.texts;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StatSorter {

    private static int getPriority(String s, Map<String, Integer> priorityMap) {
        return priorityMap.entrySet().stream()
                .filter(entry -> s.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .min(Integer::compare)
                .orElse(Integer.MAX_VALUE);
    }

    public static int elementComparator(String s1) {
        Map<String, Integer> priorityMap = new HashMap<>();
        Arrays.asList(Elements.values()).forEach(x -> priorityMap.put(x.guidName, x.ordinal()));
        MMORPG.LOGGER.log("found a " + s1 + ", and its priority is " + getPriority(s1, priorityMap));
        return getPriority(s1, priorityMap);

    }
}
