package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.mine_and_slash.database.registry.ExileDB;

import java.util.ArrayList;
import java.util.List;

public class GearRaritySuggestions extends CommandSuggestions {

    @Override
    public List<String> suggestions() {
        List<String> list = new ArrayList();

        ExileDB.GearRarities()
                .getList()
                .forEach(x -> {
                    list.add(x.GUID());
                
                });
        list.add("random");

        return list;
    }

}

