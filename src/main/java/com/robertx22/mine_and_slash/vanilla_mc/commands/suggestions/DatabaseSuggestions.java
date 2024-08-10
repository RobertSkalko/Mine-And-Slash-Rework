package com.robertx22.mine_and_slash.vanilla_mc.commands.suggestions;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IGUID;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSuggestions extends CommandSuggestions {

    ExileRegistryType type;
    String extraSuggestionOption;

    public DatabaseSuggestions(ExileRegistryType type, String extraSuggestionOption) {
        this.type = type;
        this.extraSuggestionOption = extraSuggestionOption;
    }

    @Override
    public List<String> suggestions() {

        if(extraSuggestionOption != null) {
            return registrySuggestionsWithExtraOption(extraSuggestionOption);
        }

        return registrySuggestions();
    }

    private List<String> registrySuggestions() {
        return Database.getRegistry(type)
                .getList()
                .stream()
                .map(x -> {
                    IGUID g = (IGUID) x;
                    return g.GUID();
                })
                .toList();
    }

    private List<String> registrySuggestionsWithExtraOption(String option) {
        List<String> registrySuggestions = new ArrayList<>(registrySuggestions());
        registrySuggestions.add(option);

        return registrySuggestions;
    }

}
