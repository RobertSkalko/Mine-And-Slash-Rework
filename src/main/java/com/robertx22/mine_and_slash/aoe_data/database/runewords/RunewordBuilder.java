package com.robertx22.mine_and_slash.aoe_data.database.runewords;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes.RuneType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RunewordBuilder {


    public static void of(String id, String name, List<StatMod> stats, List<RuneType> runes, String... gear_slots) {
        RuneWord word = new RuneWord();
        word.id = id;
        word.name = name;
        word.stats = stats;
        word.runes = runes.stream()
                .map(x -> x.id)
                .collect(Collectors.toList());
        word.slots.addAll(Arrays.asList(gear_slots));
        word.addToSerializables();

    }

}
