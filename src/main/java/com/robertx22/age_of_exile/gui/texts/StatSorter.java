package com.robertx22.age_of_exile.gui.texts;

import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

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
        System.out.println("found a " + s1 + ", and its priority is " + getPriority(s1, priorityMap));
        return getPriority(s1, priorityMap);

    }
}
